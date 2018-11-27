package com.liyang.housejob.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {
    private PathMatcher matcher = new AntPathMatcher();
    private final Map<String,String> authEntryPointMap;

    public LoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        this.authEntryPointMap=new HashMap<>();

        authEntryPointMap.put("/user/**","/user/login");
        authEntryPointMap.put("/admin/**","/admin/login");
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String uri = request.getRequestURI().replace(request.getContextPath(),"");
//        System.out.println("33"+uri);
        for (Map.Entry<String,String> authEntry :authEntryPointMap.entrySet()){
            if (this.matcher.match(authEntry.getKey(),uri)){
//                System.out.println("111111111111111111"+authEntry.getValue());
                return authEntry.getValue();
            }

            //有一个很傻逼的点，point拦截到 /login 的时候，会匹配不到，所以自动调用父类方法，返回 /user/login 的错误页面
            //现在修复
            if (this.matcher.match("/login",uri)){
                String referUrl = request.getHeader("referer");
//                System.out.println("refer"+referUrl);
                if (referUrl.contains("admin")){
                    return "/admin/login";
                }

            }
        }
        return super.determineUrlToUseForThisRequest(request, response, exception);
    }
}
