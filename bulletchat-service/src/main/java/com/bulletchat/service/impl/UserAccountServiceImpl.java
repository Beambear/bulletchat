package com.bulletchat.service.impl;

import com.bulletchat.code.StatusCode;
import com.bulletchat.domain.request.UserLoginRequest;
import com.bulletchat.entity.User;
import com.bulletchat.entity.UserAccount;
import com.bulletchat.exception.ConditionException;
import com.bulletchat.repository.IUserAccountRepository;
import com.bulletchat.repository.IUserRepository;
import com.bulletchat.service.IUserAccountService;
import com.bulletchat.util.MD5Util;
import com.bulletchat.util.RSAUtil;
import com.bulletchat.util.UserTokenUtil;
import com.bulletchat.util.UuidGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;



@Service
public class UserAccountServiceImpl implements IUserAccountService {
    @Autowired
    private IUserAccountRepository userAccountRepo;
    @Autowired
    private IUserRepository userRepo;

    @Override
    public String login(UserLoginRequest userInfo) throws Exception {
        //获取数据库account和user
        String uuid ="";
        if(userInfo.getAccount() != null) uuid = userAccountRepo.findUuidByAccount(userInfo.getAccount());
        if(userInfo.getPhone() != null) uuid = userRepo.findUuidByPhone(userInfo.getPhone());
        if(userInfo.getEmail() != null) uuid = userRepo.findUuidByEmail(userInfo.getEmail());
        if(uuid.equals("")) throw new ConditionException(StatusCode.NO_VALID_USER.getCode(),StatusCode.NO_VALID_USER.getInfo());
        UserAccount dbAccount = userAccountRepo.findByUuid(uuid);
        User dbUser = userRepo.findByUuid(uuid);

        //校对密码
        String dbPassword = dbAccount.getPassword();
        String md5Password = passwordRsaToMd5(userInfo.getPassword(),dbAccount.getSalt());
        if(!md5Password.equals(dbPassword)) throw new ConditionException(StatusCode.WRONG_PASSWORD.getCode(),StatusCode.WRONG_PASSWORD.getInfo());

        //获取登录token
        String token = UserTokenUtil.generateToken(uuid);
        //todo 需要把token存入redis
        //更新登陆时间
        Date now = new Date();
        dbUser.setUpdateTime(now);
        userRepo.save(dbUser);
        return token;
    }

    /**
     * Delete userAccount & User from db
     * @param uuid
     */
    @Override
    @Transactional
    public void deleteAccount(String uuid) {
        UserAccount dbAccount = userAccountRepo.findByUuid(uuid);
        userAccountRepo.delete(dbAccount);
        User dbUser = userRepo.findByUuid(uuid);
        userRepo.delete(dbUser);
    }

    /**
     * Create new account based on loginInfo
     * @param loginInfo
     * @return
     */
    @Override
    public UserAccount addAccount(UserLoginRequest loginInfo) {
        UserAccount account = new UserAccount();
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        if(loginInfo.getPassword() != null){    //如果密码不为空
            String rsaPassword = loginInfo.getPassword();
            account.setPassword(passwordRsaToMd5(rsaPassword,salt));
        }
        account.setSalt(salt);
        account.setCreateTime(now);
        account.setUpdateTime(now);
        account.setUuid(UuidGeneratorUtil.generateUuid());
        UserAccount dbAccount = userAccountRepo.save(account);
        return dbAccount;
    }

    private String passwordRsaToMd5(String rsaPassword, String salt){
        if(! rsaPassword.isEmpty()){   //有密码的需要进行密码处理
            String rawPassword;
            try{
                rawPassword = RSAUtil.decrypt(rsaPassword);
            }catch (Exception e){
                throw new ConditionException(StatusCode.DECRYPT_FAILED.getCode(),StatusCode.DECRYPT_FAILED.getInfo());
            }
            //验证密码是否合规
            isPasswordValid(rawPassword);
            return MD5Util.sign(rawPassword, salt, "UTF-8");
        }else{
            return null;
        }
    }

    private boolean isPasswordValid(String rawPassword){
        String regex1 = "^[a-zA-Z0-9]+$";
        String initial = Character.toString(rawPassword.charAt(0));
        if(!initial.matches(regex1)) throw new ConditionException(StatusCode.INVALID_PASSWORD.getCode(),StatusCode.INVALID_PASSWORD.getInfo()+":密码开头只能为数字或者字母");    //开头只能为数字字母
        String regex2 = "^[a-zA-Z0-9!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~]+$";
        if(!rawPassword.matches(regex2)) throw new ConditionException(StatusCode.INVALID_PASSWORD.getCode(),StatusCode.INVALID_PASSWORD.getInfo()+"密码包含非法符号");
        if(rawPassword.length() <9) throw new ConditionException(StatusCode.INVALID_PASSWORD.getCode(),StatusCode.INVALID_PASSWORD.getInfo()+"密码长度不足");
        return true;
    }
}
