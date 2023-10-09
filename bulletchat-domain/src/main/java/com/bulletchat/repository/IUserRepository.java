package com.bulletchat.repository;

import com.bulletchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User save(User user);

    String findUuidByPhone(String phone);

    String findUuidByEmail(String email);

    User findUserByUuid(String uuid);
}
