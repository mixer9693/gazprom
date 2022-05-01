package ru.gazprom.test.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InputParamManagerTest {

    private InputParamManager inputParamManager = new InputParamManager();

    @Test
    void parse() {
        assertThrows(IllegalArgumentException.class, () -> inputParamManager.parse(new String[]{"10"}));
        assertThrows(IllegalArgumentException.class, () -> inputParamManager.parse(new String[]{"10", "20", "30"}));
        assertThrows(IllegalArgumentException.class, () -> inputParamManager.parse(new String[]{"notStr", "filename"}));
        InputParams params = inputParamManager.parse(new String[]{"105", "filename.csv"});
        assertEquals(105, params.getAmount());
        assertEquals("filename.csv", params.getFilename());
    }

    @Test
    void validate() {
        InputParamManager paramManager = spy(inputParamManager);
        InputParams params = new InputParams(10, "file.csv");
        paramManager.validate(params);

        verify(paramManager, times(1)).validateAmount(params.getAmount());
        verify(paramManager, times(1)).validateFileName(params.getFilename());
    }

    @Test
    void validateAmount() {
        assertThrows(IllegalArgumentException.class, () -> inputParamManager.validateAmount(0));
        assertThrows(IllegalArgumentException.class, () -> inputParamManager.validateAmount(-1));
        assertDoesNotThrow(() -> inputParamManager.validateAmount(1));
    }

    @Test
    void validateFileName() {
        assertThrows(IllegalArgumentException.class, () -> inputParamManager.validateFileName("/user\file"));
        assertThrows(IllegalArgumentException.class, () -> inputParamManager.validateFileName("/user/file"));
        assertDoesNotThrow(() -> inputParamManager.validateFileName("testFile.txt"));
    }
}