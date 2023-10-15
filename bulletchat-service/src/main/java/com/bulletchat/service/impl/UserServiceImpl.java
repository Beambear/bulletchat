package com.bulletchat.service.impl;

import com.bulletchat.code.StatusCode;
import com.bulletchat.entity.User;
import com.bulletchat.exception.ConditionException;
import com.bulletchat.repository.IUserRepository;
import com.bulletchat.service.IUserService;
import com.bulletchat.util.BeanPropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepo;
    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUser(String uuid, User user) {
        if(!uuid.equals(user.getUuid())) throw new ConditionException(StatusCode.EXCEPTION_TOKEN.getCode(),"Token与修改用户不匹配");
        User dbUser = userRepo.findByUuid(uuid);
        BeanPropertyUtil.copyProperties(user,dbUser);
        return userRepo.save(dbUser);
    }

    @Override
    public User getUser(String uuid) {
        return userRepo.findByUuid(uuid);
    }

}
