package com.liyang.housejob.service;

import com.liyang.housejob.pojo.User;

public interface UserService {
    public User findByName(String username);
}
