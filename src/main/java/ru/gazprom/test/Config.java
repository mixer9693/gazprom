package ru.gazprom.test;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategyBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class Config {
    @Value("${executor.threadPool.size}")
    private Integer poolSize;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Scope("prototype")
    public <T> StatefulBeanToCsv<T> getStatefulBeanToCsv(FileWriter writer, Class<T> clazz){
        HeaderColumnNameMappingStrategy<T> str =
                new HeaderColumnNameMappingStrategyBuilder<T>()
                        .withForceCorrectRecordLength(true).build();
        str.setType(clazz);

        return new StatefulBeanToCsvBuilder<T>(writer)
                .withMappingStrategy(str)
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(poolSize);
    }

}
