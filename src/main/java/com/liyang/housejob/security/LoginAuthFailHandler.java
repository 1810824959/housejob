package com.liyang.housejob.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private LoginUrlEntryPoint loginUrlEntryPoint;

    public LoginAuthFailHandler(LoginUrlEntryPoint loginUrlEntryPoint) {
        this.loginUrlEntryPoint = loginUrlEntryPoint;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = loginUrlEntryPoint.determineUrlToUseForThisRequest(request,response,exception);
//        System.out.println(targetUrl);
        targetUrl += '?' + exception.getMessage();
        super.setDefaultFailureUrl(targetUrl);
        super.onAuthenticationFailure(request, response, exception);

    }
}
