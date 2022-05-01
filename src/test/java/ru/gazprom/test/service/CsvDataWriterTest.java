package ru.gazprom.test.service;

import com.opencsv.bean.StatefulBeanToCsv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import ru.gazprom.test.dto.UserCsvDto;
import ru.gazprom.test.mapper.UserMapper;
import ru.gazprom.test.model.User;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CsvDataWriterTest {
    private CsvDataWriter csvDataWriter;
    private UserMapper userMapper;
    private ObjectProvider<StatefulBeanToCsv<UserCsvDto>> objectProvider;
    private StatefulBeanToCsv<UserCsvDto> beanToCsv;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        objectProvider = mock(ObjectProvider.class);
        beanToCsv = mock(StatefulBeanToCsv.class);
        when(objectProvider.getObject(any())).thenReturn(beanToCsv);

        csvDataWriter = new CsvDataWriter(userMapper, objectProvider);
    }

    @Test
    void write() throws Exception {
        List<User> users = getUsers(10);
        String filename = "example.csv";
        csvDataWriter.write(users, filename);

        verify(objectProvider, times(1)).getObject(any());
        verify(userMapper, times(users.size())).userToCsvDto(any());
        verify(beanToCsv, times(1)).write(any(List.class));

        Files.deleteIfExists(Path.of(filename));
    }

    static List<User> getUsers(int number){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < number; i++){
            list.add(new User());
        }
        return list;
    }
}