package com.bulletchat.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.bulletchat.code.StatusCode;
import com.bulletchat.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

public class UserTokenUtil {
    private static final String ISSUER = "bulletchat";

    public static String generateToken(String uuid) throws Exception{
        //使用256位RSA加密
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //设置7天过期时间
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        //token里包含uuid，签发者，过期时间，加密算法
        return JWT.create().withKeyId(String.valueOf(uuid))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static String verifyToken(String token){
        try {
            //使用256位RSA解密
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String uuid = jwt.getKeyId();
            return uuid;
        } catch (TokenExpiredException e) {
            throw new ConditionException(StatusCode.EXPIRED_TOKEN.getCode(),StatusCode.EXPIRED_TOKEN.getInfo());
        } catch (Exception e) {
            throw new ConditionException(StatusCode.INVALID_TOKEN.getCode(),StatusCode.INVALID_TOKEN.getInfo());
        }

    }
}
