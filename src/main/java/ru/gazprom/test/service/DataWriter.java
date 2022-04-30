package ru.gazprom.test.service;

import ru.gazprom.test.model.User;

import java.util.List;

public interface DataWriter {
    void write(List<User> users, String filename) throws Exception;
}
