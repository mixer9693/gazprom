package ru.gazprom.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import ru.gazprom.test.model.ResponseBody;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApiDataProviderImplTest {
    private ApiDataProviderImpl apiDataProvider;
    private ExecutorService executor;
    private static final String apiUrl = "https://api.randomuser.me/";
    private static final Integer batchSize = 25;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException, ExecutionException, InterruptedException {
        executor = mock(ExecutorService.class);
        Future<ResponseBody> future = mock(Future.class);
        when(future.get()).thenReturn(mock(ResponseBody.class));
        when(executor.submit(any(Callable.class))).thenReturn(future);

        apiDataProvider = new ApiDataProviderImpl(mock(RestTemplate.class), executor);

        Field apiUrlF = apiDataProvider.getClass().getDeclaredField("apiUrl");
        apiUrlF.setAccessible(true);
        apiUrlF.set(apiDataProvider, apiUrl);

        Field batchSizeF = apiDataProvider.getClass().getDeclaredField("batchSize");
        batchSizeF.setAccessible(true);
        batchSizeF.set(apiDataProvider, batchSize);
    }

    @Test
    void decompose(){
        assertThrows(IllegalArgumentException.class, () -> ApiDataProviderImpl.Decomposer.decompose(0, batchSize));
        assertThrows(IllegalArgumentException.class, () -> ApiDataProviderImpl.Decomposer.decompose(-1, batchSize));

        ApiDataProviderImpl.Decomposer dcp = ApiDataProviderImpl.Decomposer.decompose(1, batchSize);
        assertEquals(0, dcp.getIterations());
        assertEquals(1, dcp.getRemainder());

        ApiDataProviderImpl.Decomposer dcp2 = ApiDataProviderImpl.Decomposer.decompose(10, batchSize);
        assertEquals(0, dcp2.getIterations());
        assertEquals(10, dcp2.getRemainder());

        ApiDataProviderImpl.Decomposer dcp3 = ApiDataProviderImpl.Decomposer.decompose(25, batchSize);
        assertEquals(1, dcp3.getIterations());
        assertEquals(0, dcp3.getRemainder());

        ApiDataProviderImpl.Decomposer dcp4 = ApiDataProviderImpl.Decomposer.decompose(26, batchSize);
        assertEquals(1, dcp4.getIterations());
        assertEquals(1, dcp4.getRemainder());

        ApiDataProviderImpl.Decomposer dcp5 = ApiDataProviderImpl.Decomposer.decompose(55, batchSize);
        assertEquals(2, dcp5.getIterations());
        assertEquals(5, dcp5.getRemainder());
    }

    @Test
    void fetchUserList() throws Exception {
        ApiDataProviderImpl ap = spy(apiDataProvider);
        ap.fetchUserList(55);

        verify(ap, times(1)).createTasks(2);
        verify(ap, times(1)).createRemainderTask(5);
        verify(ap, times(1)).submitAndCollect(any());
    }


    @Test
    void createTasks(){
        List<Callable<ResponseBody>> tasks = apiDataProvider.createTasks(10);
        assertEquals(10, tasks.size());
    }

    @Test
    void createRemainderTask(){
        assertFalse(apiDataProvider.createRemainderTask(0).isPresent());
        assertTrue(apiDataProvider.createRemainderTask(1).isPresent());
        assertTrue(apiDataProvider.createRemainderTask(10).isPresent());
    }

    @Test
    void submitAndCollect() throws ExecutionException, InterruptedException {
        List<Callable<ResponseBody>> tasks = getTasks(10);

        List<ResponseBody> respList = apiDataProvider.submitAndCollect(tasks);

        verify(executor, times(tasks.size())).submit(any(Callable.class));
        assertEquals(tasks.size(), respList.size());
    }

    static List<Callable<ResponseBody>> getTasks(int number){
        List<Callable<ResponseBody>> list = new ArrayList<>();
        for (int i = 0; i < number; i++){
            list.add(mock(Callable.class));
        }
        return list;
    }
}