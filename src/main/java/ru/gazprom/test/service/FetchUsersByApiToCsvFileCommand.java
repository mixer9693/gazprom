package ru.gazprom.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.gazprom.test.model.User;
import ru.gazprom.test.util.InputParamManager;
import ru.gazprom.test.util.InputParams;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FetchUsersByApiToCsvFileCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FetchUsersByApiToCsvFileCommand.class.getName());
    private final DataProvider apiDataProvider;
    private final InputParamManager inputParamManager;
    @Qualifier(value = "csvWriter")
    private final DataWriter dataWriter;

    public void execute(String[] args){
        try {
            InputParams inputParams = inputParamManager.parse(args);
            inputParamManager.validate(inputParams);

            LOGGER.log(Level.INFO, "Fetching users via API...");
            List<User> userList = apiDataProvider.fetchUserList(inputParams.getAmount());

            LOGGER.log(Level.INFO, "Writing to file...");
            dataWriter.write(userList, inputParams.getFilename());

            LOGGER.log(Level.INFO, "Success. {0} users written to {1}",
                    new Object[]{inputParams.getAmount(), inputParams.getFilename()});

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

}
