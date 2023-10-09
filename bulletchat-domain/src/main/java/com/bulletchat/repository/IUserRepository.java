package com.bulletchat.repository;

import com.bulletchat.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository {
    User saveUser(User user);

    String findUuidByPhone(String phone);

    String findUuidByEmail(String email);

    User findUserByUuid(String uuid);
}
