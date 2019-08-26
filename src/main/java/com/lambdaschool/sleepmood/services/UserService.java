package com.lambdaschool.sleepmood.services;

import com.lambdaschool.sleepmood.models.User;

import java.util.List;

public interface UserService
{

    List<User> findAll();

    User findUserById(long id);

    User findUserByName(String name);

    void delete(long id);

    User save(User user);

    User update(User user, long id);
}