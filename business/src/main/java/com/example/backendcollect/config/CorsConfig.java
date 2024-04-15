package com.example.backendcollect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author: Rikka
 * @date: 2022/3/4 下午7:20
 * @description 跨域配置
 */

@Configuration
public class CorsConfig implements HandlerInterceptor {
    @Value("${service.corsOrigin}")  //跨域自定义配置application.yml自己配置
    private String corsOrigin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String origin = request.getHeader("Origin");
        //后端响应基本配置项
        if (origin != null && !corsOrigin.contains(origin)) {

            return false;
        }
        //支持跨域请求
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "*");
        //是否支持cookie跨域
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //Origin, X-Requested-With, Content-Type, Accept,Access-Token
        response.setHeader("Access-Control-Allow-Headers",
                "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");
        return true;
    }
}
