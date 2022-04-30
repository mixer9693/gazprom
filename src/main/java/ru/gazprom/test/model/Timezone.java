package ru.gazprom.test.model;

import lombok.Data;

@Data
public class Timezone {
    private String offset;
    private String description;
}
