package com.oldphoto.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.oldphoto.dto.LoginFormDTO;
import com.oldphoto.dto.Password;
import com.oldphoto.dto.Result;
import com.oldphoto.dto.UserDTO;
import com.oldphoto.entity.User;
import com.oldphoto.mapper.UserMapper;
import com.oldphoto.service.UserService;
import com.oldphoto.util.JwtUtil;
import com.oldphoto.util.RedisCache;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.oldphoto.util.SystemConstants.USER_NICK_NAME_PREFIX;

@Service
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;
  @Autowired
  private RedisCache redisCache;


  @Override
  public int register(LoginFormDTO loginForm) {
    if (userMapper.getUser(loginForm) != null)
      return 0;
    loginForm.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
    int i = userMapper.register(loginForm);

    return i;
  }

  @Override
  public String Login(LoginFormDTO loginForm) {
    UserDTO userDto = userMapper.getUserDto(loginForm);
    String userId = userDto.getId().toString();
    String jwt = JwtUtil.createJWT(userId);

    //authenticate存入Redis
    redisCache.setCacheObject("login:"+userId,userDto);


    return jwt;
  }

  @Override
  public UserDTO userInfo(String Authorization) throws Exception {
    Claims claims = JwtUtil.parseJWT(Authorization);
    String userId = claims.getSubject();
    String redisKey = "login:" + userId;
    UserDTO userDto = redisCache.getCacheObject(redisKey);
    User user = userMapper.getUserById(Long.parseLong(userId));
    String avatar = user.getAvatar();
    userDto.setAvatar(avatar);
    return userDto;
  }

  @Override
  public UserDTO updateUserInfo(User user, String authorization) throws Exception {
    Claims claims = JwtUtil.parseJWT(authorization);
    String userId = claims.getSubject();
    String redisKey = "login:" + userId;
    UserDTO userDto = redisCache.getCacheObject(redisKey);
    if(Objects.isNull(userDto))
      return null;
    int i = userMapper.updateUserInfo(user);
    Long userid = Long.parseLong(userId);
    User user1 = userMapper.getUserById(userid);
    UserDTO userDTO = BeanUtil.copyProperties(user1, UserDTO.class);
    return userDTO;
  }

  @Override
  public int updateAvatar(String avatar, String authorization) throws Exception {
    Claims claims = JwtUtil.parseJWT(authorization);
    String userId = claims.getSubject();
    String redisKey = "login:" + userId;
    UserDTO userDto = redisCache.getCacheObject(redisKey);
    if(Objects.isNull(userDto))
      return 0;
    User user = userMapper.getUserById(Long.parseLong(userId));
    user.setAvatar(avatar);
    int i = userMapper.updateAvatar(user);
    return 1;
  }

  @Override
  public int updatePwd(Password password, String authorization) throws Exception {
    Claims claims = JwtUtil.parseJWT(authorization);
    String userId = claims.getSubject();
    String redisKey = "login:" + userId;
    UserDTO userDto = redisCache.getCacheObject(redisKey);
    if(Objects.isNull(userDto))
      return 0;
    User user = userMapper.getUserById(Long.parseLong(userId));
    if(!Objects.equals(user.getPassword(), password.getOldPwd()))
      return 0;
    user.setPassword(password.getNewPwd());
    int i = userMapper.updatePwd(user);
    return i;
  }

}
