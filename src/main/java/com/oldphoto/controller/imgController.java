package com.oldphoto.controller;

import cn.hutool.core.img.Img;
import com.oldphoto.dto.Image;
import com.oldphoto.dto.Result;
import com.oldphoto.dto.text;
import com.oldphoto.service.ImgService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/img")
public class imgController {

  @Resource
  private ImgService imgService;
    @PostMapping("/upload")
    public Result upload(@RequestBody Image img){
      String model = imgService.toModel(img.getImg());
      return Result.ok(model);
    }

    @PostMapping("/text")
    public Result text(@RequestBody text text){
      String img = imgService.text(text);

      return Result.ok(img);
    }
}
