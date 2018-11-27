package com.liyang.housejob.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;


@Configuration
@ConditionalOnClass({Servlet.class, StandardServletMultipartResolver.class, MultipartConfigElement.class})
@ConditionalOnProperty(prefix = "spring.http.multipart",name = "enabled",matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class) //这里相当于是自动加载一个配置
public class WebFileUploadConfig {
}
