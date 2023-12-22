package com.oldphoto.service;

import com.oldphoto.dto.LoginFormDTO;
import com.oldphoto.dto.Password;
import com.oldphoto.dto.Result;
import com.oldphoto.dto.UserDTO;
import com.oldphoto.entity.User;

public interface UserService {
    public int register(LoginFormDTO loginForm);

    public String Login(LoginFormDTO loginForm);

    public UserDTO userInfo(String Authorization) throws Exception;

    public UserDTO updateUserInfo(User user, String authorization) throws Exception;

    public int updateAvatar(String avatar, String authorization) throws Exception;

    public int updatePwd(Password password, String authorization) throws Exception;
}
