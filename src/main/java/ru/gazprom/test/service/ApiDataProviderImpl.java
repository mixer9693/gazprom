package ru.gazprom.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gazprom.test.model.ResponseBody;
import ru.gazprom.test.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ApiDataProviderImpl implements ApiDataProvider {
    private static final Logger LOGGER = Logger.getLogger(ApiDataProviderImpl.class.getName());

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.batchSize}")
    private Integer batchSize;

    private final RestTemplate restTemplate;
    private final ExecutorService executor;

    public List<User> fetchUserList(int amount) throws Exception {
        if (amount <= 0){
            throw new IllegalArgumentException();
        }
        int wholeIters = amount / batchSize;
        int remainder = amount % batchSize;

        List<User> userList = new ArrayList<>();
        List<Future<ResponseBody>> tasks = new ArrayList<>();

        final String url = String.format("%s?results=%s", apiUrl, batchSize);
        for (int i = 0; i < wholeIters; i++){
            LOGGER.log(Level.FINE, "Try to submit task {0} to fetch data from {1}",
                    new Object[]{(i+1), url});
            Future<ResponseBody> f = executor.submit(() ->
                    restTemplate.getForObject(url, ResponseBody.class));
            tasks.add(f);
        }

        if (remainder > 0){
            LOGGER.log(Level.FINE, "Try to submit remaining task to fetch data from {0}", url);
            final String urlR = String.format("%s?results=%s", apiUrl, remainder);
            Future<ResponseBody> f = executor.submit(() ->
                    restTemplate.getForObject(urlR, ResponseBody.class));
            tasks.add(f);
        }

        LOGGER.log(Level.FINE, "Try to obtain and collect results from {0} tasks", tasks.size());
        for (Future<ResponseBody> future: tasks){
            userList.addAll(future.get().getResults());
            LOGGER.log(Level.FINE, "Task completed");
        }

        return userList;
    }


}
