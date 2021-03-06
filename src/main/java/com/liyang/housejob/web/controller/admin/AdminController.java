package com.liyang.housejob.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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


    @GetMapping("admin/house/list")
    public String HouseListPage(){
        return "admin/house-list";
    }
}
