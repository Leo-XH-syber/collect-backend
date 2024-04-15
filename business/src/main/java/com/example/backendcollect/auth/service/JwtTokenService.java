package com.example.backendcollect.auth.service;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.auth.util.JwtUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: Rikka
 * @date: 2022/3/2 上午12:39
 * @description
 */

@Component
public class JwtTokenService {

    @Resource
    JwtUtil jwtUtil;

    private final int EXPIRE = 7200; // 设置 token 过期时间

    public String generateToken(JwtInfo jwtInfo) {
        return jwtUtil.generateToken(jwtInfo, EXPIRE);
    }

    public JwtInfo getInfoFromToken(String token) {
        return jwtUtil.getTokenInfo(token);
    }

}
