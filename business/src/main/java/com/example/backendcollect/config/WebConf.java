package com.example.backendcollect.config;

import com.example.backendcollect.auth.interceptor.JwtInterceptor;
import com.example.backendcollect.utils.Converter.StringToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * @author: Rikka
 * @date: 2022/2/27 上午12:58
 * @description 增加 Controller 接口的类型转换, 添加拦截器
 */

@Configuration
public class WebConf implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }


    @Resource
    JwtInterceptor jwtInterceptor;
    @Resource
    CorsConfig corsConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsConfig);
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**"); // 所有请求都拦截, 通过注解判断是否放行
    }
}
