package com.liyang.housejob.controller.admin;

import com.liyang.housejob.base.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

    @GetMapping("/admin/center")
    public String adminCenterPage(){
        return "admin/center";
    }

    @GetMapping("/admin/welcome")
    public String adminWelcomePage(){
        return "admin/welcome";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage(){
        return "admin/login";
    }

    @GetMapping("admin/add/house")
    public String addHousePage(){
        return "admin/house-add";
    }

    @PostMapping(value = "/admin/upload/photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse uploadPhoto(@RequestParam("file")MultipartFile file){
        return ApiResponse.ofSuccess(null);
    }

}
