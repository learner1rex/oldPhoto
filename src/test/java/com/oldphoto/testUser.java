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

//  @Test
//  public void testRegister() {
//    LoginFormDTO loginForm = new LoginFormDTO();
//    loginForm.setPassword("123456");
//    loginForm.setUserName("张三");
//    Result result = userService.register(loginForm);
//    System.out.println(result);
//  }

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
      String image = "test";

      // 创建请求体
      RequestBody requestBody = RequestBody.create(
        MediaType.parse("application/json; charset=utf-8"),
        "{\n" +
          "  \"resize_mode\": 0,\n" +
          "  \"show_extras_results\": true,\n" +
          "  \"gfpgan_visibility\": 0,\n" +
          "  \"codeformer_visibility\": 0,\n" +
          "  \"codeformer_weight\": 0,\n" +
          "  \"upscaling_resize\": 2,\n" +
          "  \"upscaling_resize_w\": 512,\n" +
          "  \"upscaling_resize_h\": 512,\n" +
          "  \"upscaling_crop\": true,\n" +
          "  \"upscaler_1\": \"None\",\n" +
          "  \"upscaler_2\": \"None\",\n" +
          "  \"extras_upscaler_2_visibility\": 0,\n" +
          "  \"upscale_first\": false,\n" +
          "  \"image\": \""+image+"\"\n" +
          "}"
        );

      // 创建请求
      Request request = new Request.Builder()
        .url("http://localhost:8080/image/upload") // 设置目标URL
        .post(requestBody) // 设置请求体和POST方法
        .build();

    try(Response response = okHttpClient.newCall(request).execute()) {
      if(!response.isSuccessful()){
        throw new IOException("unexpected code" + response);
      }

      String string = response.body().string();
      System.out.println(string);
    }catch (Exception e){

    }
  }
}
