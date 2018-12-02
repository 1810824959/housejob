package com.liyang.housejob.base;

import com.liyang.housejob.pojo.User;
import jdk.nashorn.internal.ir.LoopNode;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUserUtil {
    public static User load(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof  User){
            return (User) principal;
        }
        return null;
    }

    public static int getLoginUserId(){
        User user = load();
        if (user == null){
            return -1;
        }

        return user.getId();
    }
}
