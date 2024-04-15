package com.example.backendcollect.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author: Rikka
 * @date: 2022/3/5 上午5:21
 * @description
 */
public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value, String domain, int maxAge) {
        try {
            Cookie cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
            cookie.setPath("/");
            cookie.setDomain(domain);
            cookie.setMaxAge(maxAge);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        } catch (Exception var6) {
            throw new RuntimeException(var6.getMessage());
        }
    }
}