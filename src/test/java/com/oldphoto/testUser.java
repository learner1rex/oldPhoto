package com.oldphoto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oldphoto.dto.LoginFormDTO;
import com.oldphoto.dto.Result;
import com.oldphoto.dto.UserDTO;
import com.oldphoto.entity.User;
import com.oldphoto.mapper.UserMapper;
import com.oldphoto.service.UserService;
import jakarta.annotation.Resource;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class testUser {
  @Resource
  private UserMapper userMapper;
  @Resource
  private UserService userService;

  private OkHttpClient okHttpClient = new OkHttpClient();

  @Test
  public void testRegister() {
    LoginFormDTO loginForm = new LoginFormDTO();
    loginForm.setPassword("123456");
    loginForm.setUserName("张三");
    Result result = userService.register(loginForm);
    System.out.println(result);
  }

  //    @Test
//    public void testLogin(){
//        LoginFormDTO loginForm = new LoginFormDTO();
//        loginForm.setPassword("123456");
//        loginForm.setUserName("张三");
//        UserDTO logined = userMapper.login(loginForm);
//        System.out.println(logined);
//    }
  @Test
  public void testImg() {
    String img = "22222222222";
    RequestBody body = new FormBody.Builder().
      add("img",img).build();

    Request request = new Request.
      Builder().
      url("http://localhost:8080/image/upload").
      post(body).
      build();

    try(Response response = okHttpClient.newCall(request).execute()) {
      if(!response.isSuccessful()){
        throw new IOException("unexpected code" + response);
      }
      JSONObject json = JSON.parseObject(response.body().string());

      System.out.println(json);
    }catch (Exception e){

    }
  }
}
