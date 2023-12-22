package com.oldphoto.service.Impl;

import cn.hutool.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(img, mediaType);
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
