package ru.gazprom.test.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gazprom.test.model.ResponseBody;
import ru.gazprom.test.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiDataProvider implements DataProvider {
    private static final Logger LOGGER = Logger.getLogger(ApiDataProvider.class.getName());

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.batchSize}")
    private Integer batchSize;

    private final RestTemplate restTemplate;
    private final ExecutorService executor;

    public List<User> fetchUserList(int amount) throws Exception {
        Decomposer decomposer = Decomposer.decompose(amount, batchSize);

        LOGGER.log(Level.FINE, "Try to create {0} tasks to fetch {1}",
                new Object[]{decomposer.getIterations(), amount});
        List<Callable<ResponseBody>> tasks = createTasks(decomposer.getIterations());

        LOGGER.log(Level.FINE, "Try to create remaining task");
        createRemainderTask(decomposer.getRemainder())
                .ifPresent(tasks::add);

        LOGGER.log(Level.FINE, "Try to submit {0} tasks and get results", tasks.size());
        List<ResponseBody> results = submitAndCollect(tasks);

        return results.stream()
                .flatMap(e -> e.getResults().stream())
                .collect(Collectors.toList());
    }

    public List<Callable<ResponseBody>> createTasks(int number){
        List<Callable<ResponseBody>> tasks = new ArrayList<>();
        final String url = String.format("%s?results=%s", apiUrl, batchSize);

        for (int i = 0; i < number; i++){
            LOGGER.log(Level.FINE, "Try to create a task {0} to fetch {1} from {2}",
                    new Object[]{(i+1), batchSize, url});
            tasks.add(() -> restTemplate.getForObject(url, ResponseBody.class));
        }

        return tasks;
    }

    public Optional<Callable<ResponseBody>> createRemainderTask(int remainder){
        if (remainder > 0){
            final String urlR = String.format("%s?results=%s", apiUrl, remainder);
            LOGGER.log(Level.FINE, "Try to create a task to fetch remaining {0} from {1}",
                    new Object[]{remainder, urlR});
            return Optional.of(() -> restTemplate.getForObject(urlR, ResponseBody.class));
        }

        return Optional.empty();
    }

    public List<ResponseBody> submitAndCollect(List<Callable<ResponseBody>> tasks) throws ExecutionException, InterruptedException {
        List<Future<ResponseBody>> futureList = new ArrayList<>();
        for (Callable<ResponseBody> task: tasks){
            futureList.add(executor.submit(task));
        }
        List<ResponseBody> results = new ArrayList<>();
        for (Future<ResponseBody> future: futureList){
            results.add(future.get());
        }

        return results;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class Decomposer {
        private int iterations;
        private int remainder;

        public static Decomposer decompose(int amount, int batchSize){
            if (amount <= 0){
                throw new IllegalArgumentException();
            }
            int iterations = amount / batchSize;
            int remainder = amount % batchSize;

            return new Decomposer(iterations, remainder);
        }
    }

}
