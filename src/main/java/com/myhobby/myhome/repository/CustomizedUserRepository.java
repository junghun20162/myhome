package com.myhobby.myhome.repository;

import com.myhobby.myhome.model.User;

import java.util.List;

interface CustomizedUserRepository {

    void findByUsernameCustom(User user);
    List<User> findByUsernameJdbc(String username);

}
