package com.bulletchat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String uuid;
    @Column(unique = true)
    private String openid;
    private String email;
    private String phone;
    private String nickname;
    private String avatarUuid;
    private String avatarUrl;
    private String gender;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birth;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;    //记录最后登录时间
}
