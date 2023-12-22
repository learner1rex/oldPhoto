package com.oldphoto.controller;

import com.oldphoto.dto.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/img")
public class imgController {
    @PostMapping("/upload")
    public Result upload(@RequestBody String img){
        System.out.println(img);
        return Result.ok(img);
    }
}
