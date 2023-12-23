package com.oldphoto.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.oldphoto.dto.text;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ImgServiceImpl implements com.oldphoto.service.ImgService {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .build();
    @Override
    public String toModel(String img) {
        if (Objects.equals(img, ""))
            return null;

        try {
          // 原始JSON对象
          JSONObject json = new JSONObject();
          json.put("prompt", "1girl, blue hair");
          json.put("seed", 1);
          json.put("steps", 20);
          json.put("width", 512);
          json.put("height", 512);
          json.put("denoising_strength", 0.5);
          json.put("n_iter", 1);
          json.put("init_images", img); // 需要提供init_images的实际值
          json.put("batch_size", "batch_size_value"); // 需要提供batch_size的实际值

          // 创建RequestBody
          RequestBody body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"), json.toString()
          );
            //模型路径url 前面记得加端口号
            Request request = new Request.Builder().
                    url("http://127.0.0.1:7860/sdapi/v1/img2img").post(body).build();
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful())
                throw new IOException("unexpected code" + response);
            String json1 = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            //模型传过来的json  getStr("") 按响应中的键名 取图片的base64码
            String imgGenerated = jsonObject.getString("image");
            return imgGenerated;
        }catch (Exception e){

        }
        return null;
    }

  @Override
  public String text(text text) {
    String prompt = text.getPrompt();
    String step = text.getStep();

    try {
      JSONObject json = new JSONObject();
      json.put("prompt",prompt);
      json.put("step",step);
      RequestBody body = RequestBody.create(
        MediaType.parse("application/json; charset=utf-8"), json.toString()
      );
      //模型路径url 前面记得加端口号
      Request request = new Request.Builder().
        url("http://127.0.0.1:7860/sdapi/v1/txt2img").post(body).build();
      Response response = client.newCall(request).execute();
      if(!response.isSuccessful())
        throw new IOException("unexpected code" + response);
      String json1 = response.body().string();
      JSONObject jsonObject = new JSONObject(json);
      //模型传过来的json  getStr("") 按响应中的键名 取图片的base64码
      String imgGenerated = jsonObject.getString("image");
      return imgGenerated;
    }catch (Exception e){

    }
  }
}
