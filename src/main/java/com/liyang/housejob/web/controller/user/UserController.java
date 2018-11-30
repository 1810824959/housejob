package com.liyang.housejob.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user/login")
    public String userLoginPage(){
        return "user/login";
    }

    @GetMapping("/user/center")
    public String userCenterPage(){
        return "user/center";
    }
}
