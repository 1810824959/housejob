package com.liyang.housejob.controller;

import com.liyang.housejob.base.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class hellopage {



    @RequestMapping("/index")
    public String index(){
        return "hah";
    }

    @RequestMapping("/json")
    @ResponseBody
    public ApiResponse testJson(){
        return ApiResponse.ofMessage(200,"还行");
    }
}
