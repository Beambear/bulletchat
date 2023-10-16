package com.bulletchat.controller;

import com.bulletchat.domain.JsonResponse;
import com.bulletchat.domain.request.UserLoginRequest;
import com.bulletchat.entity.UserAccount;
import com.bulletchat.entity.User;
import com.bulletchat.service.IUserAccountService;
import com.bulletchat.service.IUserService;
import com.bulletchat.support.UserSupport;
import com.bulletchat.util.RSAUtil;
import com.bulletchat.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bulletchat/")
public class UserController {

    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserSupport userSupport;
    @GetMapping("rsa-pk")
    public JsonResponse<String> getRsaPublicKey(){
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    @GetMapping("users")
    public JsonResponse<User> getUser(){
        String uuid = userSupport.getUuidByToken();
        User user = userService.getUser(uuid);
        return new JsonResponse<>(user);
    }

    @PutMapping("users")
    public JsonResponse<User> updateUser(@RequestBody User user){
        String uuid = userSupport.getUuidByToken();
        User label = userService.updateUser(uuid,user);
        return new JsonResponse<>(label);
    }

    @PostMapping("users")
    public JsonResponse<String> addUserAccount(@RequestBody UserLoginRequest loginInfo) throws Exception {
        UserAccount dbAccount = userAccountService.addAccount(loginInfo);
        User user = new User();
        user.setUuid(dbAccount.getUuid());
        user.setCreateTime(dbAccount.getCreateTime());
        user.setUpdateTime(dbAccount.getUpdateTime());
        User dbUser = userService.addUser(user);
        String token = UserTokenUtil.generateToken(dbUser.getUuid());
        return new JsonResponse<>(token);
    }

    @PostMapping("users-login")
    public JsonResponse<String> loginUser(@RequestBody UserLoginRequest loginInfo) throws Exception {
        String token = userAccountService.login(loginInfo);
        return new JsonResponse<>(token);
    }

    @DeleteMapping("users")
    public JsonResponse<String> deleteAccount(){
        String uuid = userSupport.getUuidByToken();
        userAccountService.deleteAccount(uuid);
        return JsonResponse.success();
    }
}
