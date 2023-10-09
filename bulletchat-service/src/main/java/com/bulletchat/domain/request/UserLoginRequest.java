package com.bulletchat.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    private String type;
    private String account;
    private String password;
    private String phone;
    private String email;
    private String openid;
}
