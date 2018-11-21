package com.liyang.housejob.base;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liyang.housejob.base.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.web.ErrorAttributes;
//import org.springframework.boot.autoconfigure.web.ErrorController;


import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * web错误 全局配置
 * Created by 瓦力.
 */

@Controller
public class AppErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";
    private ErrorAttributes errorAttributes;

    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes){
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    //从 AbstractErrorController 类中 copy 的方法
    //用了装饰模式，传入 HttpServletRequest ，里面再封装
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }


    /**
     * Web页面错误处理
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        switch (status) {
            case 403:
                return "403";
            case 404:
                return "404";
            case 500:
                return "500";
        }

        return "index";
    }



    /**
     * 除Web页面外的错误处理，比如Json/XML等
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ApiResponse errorApiHandler(HttpServletRequest request) {

        Map<String, Object> attr =this.getErrorAttributes(request, false);
        int status = MygetStatus(request);

        return ApiResponse.ofMessage(status, String.valueOf(attr.getOrDefault("message", "error")));
    }

    private int MygetStatus(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            return status;
        }

        return 500;
    }
}
