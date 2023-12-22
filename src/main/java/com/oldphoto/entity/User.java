package com.oldphoto.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private String avatar;
    private String email;
}
