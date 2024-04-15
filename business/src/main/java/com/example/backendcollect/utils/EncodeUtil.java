package com.example.backendcollect.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author: Rikka
 * @date: 2022/3/2 下午8:47
 * @description 密码加密解密
 */
@Component
public class EncodeUtil {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String encode(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodePassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodePassword);
    }
}
