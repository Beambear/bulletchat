package com.bulletchat.repository;

import com.bulletchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User save(User user);

    @Query("select u.uuid from User u where u.phone = ?1")
    String findUuidByPhone(String phone);

    @Query("select u.uuid from User u where u.email = ?1")
    String findUuidByEmail(String email);

    User findByUuid(String uuid);
}
