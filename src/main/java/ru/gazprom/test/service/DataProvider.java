package ru.gazprom.test.service;

import ru.gazprom.test.model.User;

import java.util.List;

public interface DataProvider {
    List<User> fetchUserList(int amount) throws Exception;
}
