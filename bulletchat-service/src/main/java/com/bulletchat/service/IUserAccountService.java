package com.bulletchat.service;

import com.bulletchat.domain.request.UserLoginRequest;
import com.bulletchat.entity.User;
import com.bulletchat.entity.UserAccount;

public interface IUserAccountService {
    UserAccount addAccount(UserLoginRequest loginInfo);
    String login(UserLoginRequest userInfo) throws Exception;

    void deleteAccount(String uuid);
}
