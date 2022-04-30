package ru.gazprom.test.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseBody {
    private List<User> results;
    private Info info;
}
