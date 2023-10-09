package com.bulletchat.repository;

import com.bulletchat.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount,Long> {
    UserAccount saveUserAccount(UserAccount userAccount);

    String findUuidByAccount(String account);

    UserAccount findUserAccountdByUuid(String uuid);
}
