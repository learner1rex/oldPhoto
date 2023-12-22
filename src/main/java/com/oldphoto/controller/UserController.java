package com.oldphoto.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.oldphoto.dto.LoginFormDTO;
import com.oldphoto.dto.Password;
import com.oldphoto.dto.Result;
import com.oldphoto.dto.UserDTO;
import com.oldphoto.entity.User;
import com.oldphoto.service.UserService;
import jakarta.annotation.Resource;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody LoginFormDTO loginForm){
        int i = userService.register(loginForm);
        return Result.ok(i);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm){
        String token = userService.Login(loginForm);
        return Result.token(token);
    }

    @GetMapping("/getInfo")
    public Result userInfo(@RequestHeader String Authorization) throws Exception {
      UserDTO userDTO = userService.userInfo(Authorization);
      return Result.ok(userDTO);

    }

    @PutMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody User user,@RequestHeader String Authorization) throws Exception{
      UserDTO userDTO = userService.updateUserInfo(user, Authorization);
      return Result.ok(userDTO);
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody User user,@RequestHeader String Authorization) throws Exception{
      int i = userService.updateAvatar(user.getAvatar(),Authorization);
      return Result.ok(i);
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Password password,@RequestHeader String Authorization) throws Exception{
      int i = userService.updatePwd(password, Authorization);
      return Result.ok(i);
    }
}
