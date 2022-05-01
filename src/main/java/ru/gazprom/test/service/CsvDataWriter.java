package ru.gazprom.test.service;

import com.opencsv.bean.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import ru.gazprom.test.dto.UserCsvDto;
import ru.gazprom.test.mapper.UserMapper;
import ru.gazprom.test.model.*;

import java.io.FileWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service(value = "csvWriter")
@RequiredArgsConstructor
public class CsvDataWriter implements DataWriter {
    private static final Logger LOGGER = Logger.getLogger(CsvDataWriter.class.getName());

    private final UserMapper userMapper;
    private final ObjectProvider<StatefulBeanToCsv<UserCsvDto>> objectProvider;

    @Override
    public void write(List<User> users, String filename) throws Exception {
        LOGGER.log(Level.FINE, "Try to write {0} users to {1}", new Object[]{users.size(), filename});

        try(FileWriter writer = new FileWriter(filename)){
            StatefulBeanToCsv<UserCsvDto> beanToCsv =
                    objectProvider.getObject(writer, UserCsvDto.class);

            List<UserCsvDto> list = users.stream()
                    .map(userMapper::userToCsvDto)
                    .collect(Collectors.toList());

            beanToCsv.write(list);
        }
    }
}
