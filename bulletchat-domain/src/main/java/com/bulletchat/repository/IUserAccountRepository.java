package com.bulletchat.repository;

import com.bulletchat.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount,Long> {
    UserAccount save(UserAccount userAccount);

    @Query("select u.uuid from UserAccount u where u.account = ?1")
    String findUuidByAccount(String account);

    UserAccount findByUuid(String uuid);
}
