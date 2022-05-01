package ru.gazprom.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.gazprom.test.util.InputParamManager;
import ru.gazprom.test.util.InputParams;
import static org.mockito.Mockito.*;

class FetchUsersByApiToCsvFileCommandTest {

    private FetchUsersByApiToCsvFileCommand command;

    private ApiDataProvider apiDataProvider;
    private InputParamManager inputParamManager;
    private CsvDataWriter dataWriter;

    @BeforeEach
    void setUp() {
        apiDataProvider = mock(ApiDataProvider.class);
        inputParamManager = mock(InputParamManager.class);
        when(inputParamManager.parse(any())).thenReturn(new InputParams(10, "file"));
        dataWriter = mock(CsvDataWriter.class);

        command = new FetchUsersByApiToCsvFileCommand(apiDataProvider, inputParamManager, dataWriter);
    }

    @Test
    void execute() throws Exception {
        command.execute(new String[]{});
        verify(inputParamManager, times(1)).parse(any());
        verify(inputParamManager, times(1)).validate(any());
        verify(apiDataProvider, times(1)).fetchUserList(anyInt());
        verify(dataWriter, times(1)).write(any(), any());
    }
}