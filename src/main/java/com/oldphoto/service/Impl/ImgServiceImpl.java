package com.oldphoto.service.Impl;

import cn.hutool.json.JSONObject;
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
          RequestBody body = RequestBody.create(
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
              "  \"image\": \"" + img + "\"\n" +
              "}"
          );
            //模型路径url 前面记得加端口号
            Request request = new Request.Builder().
                    url("/sdapi/v1/img2img").post(body).build();
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful())
                throw new IOException("unexpected code" + response);
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            //模型传过来的json  getStr("") 按响应中的键名 取图片的base64码
            String imgGenerated = jsonObject.getStr("");
            return imgGenerated;
        }catch (Exception e){

        }
        return null;
    }
}
