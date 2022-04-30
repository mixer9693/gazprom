package ru.gazprom.test.model;

import lombok.Data;

@Data
public class Info {
    private String seed;
    private Integer results;
    private Integer page;
    private String version;
}
