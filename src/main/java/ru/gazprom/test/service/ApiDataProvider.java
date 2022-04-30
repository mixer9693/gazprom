package ru.gazprom.test.service;

import ru.gazprom.test.model.User;

import java.util.List;

public interface ApiDataProvider {
    List<User> fetchUserList(int amount) throws Exception;
}
