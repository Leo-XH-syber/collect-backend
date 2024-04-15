package com.example.backendcollect.controller.user;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.auth.service.JwtTokenService;
import com.example.backendcollect.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * @author: Rikka
 * @date: 2022/3/3 上午12:28
 * @description
 */
@ControllerAdvice
public class UserAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    JwtTokenService jwtTokenService;

    @Value("${server.servlet.session.cookie.domain}")
    private String domain;

    private JwtInfo jwtInfo;

    @Autowired
    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * controller 之后, 返回之前调用, setCookie. 每次都会调用... 似乎可以改一改, 操作一次算一个 token 太慢了吧
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//            Cookie token = new Cookie("token", jwtTokenService.generateToken(jwtInfo));
        ServletServerHttpResponse ssResp = (ServletServerHttpResponse) response;

        if (jwtInfo.getId() != null) {
            CookieUtil.addCookie(ssResp.getServletResponse(), "token", jwtTokenService.generateToken(jwtInfo), domain, 3600);
        } else {

            CookieUtil.addCookie(ssResp.getServletResponse(), "token", jwtTokenService.generateToken(jwtInfo), domain, 0);
        }
        return body;

    }
}
