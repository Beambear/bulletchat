package com.bulletchat.support;

import com.bulletchat.util.UserTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

@Component
public class UserSupport {
    public String getUuidByToken(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        String uuid = UserTokenUtil.verifyToken(token);
        return uuid;
    }
}
