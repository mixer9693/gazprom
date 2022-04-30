package ru.gazprom.test.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputParams {
    private int amount;
    private String filename;
}
