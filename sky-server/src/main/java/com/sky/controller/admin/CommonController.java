package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Slf4j
@RestController
@Api(tags = "通用接口")
@RequestMapping("/admin/common")
public class CommonController {
    public static String path=null;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) throws IOException {
     log.info("文件上传:{}",file);
    String savePath="D://itheima//资料//day01//后端初始工程//sky-take-out//sky-server//src//main//resources//picture//";
   String originalName= file.getOriginalFilename();
   File file1=new File(savePath);
   file.transferTo(new File(file1,originalName));
        System.out.println(savePath);
       path=savePath;
    return Result.error("文件上传失败");
    }
}
