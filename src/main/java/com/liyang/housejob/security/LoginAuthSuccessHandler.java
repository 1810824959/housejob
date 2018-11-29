package com.liyang.housejob.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String referUrl = request.getHeader("referer");

        if (referUrl.contains("/admin/login")){
//            System.out.println(referUrl);
            super.getRedirectStrategy().sendRedirect(request,response,"/admin/center");
            super.clearAuthenticationAttributes(request);
        }else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
