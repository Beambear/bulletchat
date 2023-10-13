package com.bulletchat.service;

import com.bulletchat.entity.User;

public interface IUserService {
    User addUser(User user);

    User updateUser(String uuid, User user);

    User getUser(String uuid);

}
