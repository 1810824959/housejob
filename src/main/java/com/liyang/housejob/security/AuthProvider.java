package com.liyang.housejob.security;

import com.liyang.housejob.pojo.User;
import com.liyang.housejob.service.UserService;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;


public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String inputPwd = (String) authentication.getCredentials();
//        System.out.println(username+"     "+inputPwd);

        User user = userService.findByName(username);
        if (user == null){
            throw new AuthenticationCredentialsNotFoundException("pwd not found");
        }
        //先不管密码的加密
        if ((user.getPassword().equals(inputPwd)) && (user.getName().equals(username))){  //验证成功
//            System.out.println("dadasdadadasdasd");
//            System.out.println(user.getName()+"dsa"+user.getPassword());
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
//        System.out.println("错误");
        throw new BadCredentialsException("authError");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;   // 支持任何形式的用户验证
    }
}
