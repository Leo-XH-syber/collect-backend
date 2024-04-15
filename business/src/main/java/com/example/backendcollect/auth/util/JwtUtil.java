package com.example.backendcollect.auth.util;

import com.example.backendcollect.auth.constant.JwtConst;
import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.enums.types.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Component
public class JwtUtil {

    private Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //TODO 使用环境变量而不是把 secret 硬编码在这里
        String s = DatatypeConverter.printBase64Binary("this is my secret".getBytes());
        return new SecretKeySpec(s.getBytes(), signatureAlgorithm.getJcaName());
    }

    /**
     * @param jwtInfo token 负载里面的数据
     * @param expire  有效期
     * @return token
     */
    public String generateToken(JwtInfo jwtInfo, int expire) {
        return Jwts.builder().claim(JwtConst.JWT_KEY_UID, jwtInfo.getId())
                .claim(JwtConst.JWT_KEY_NAME, jwtInfo.getUname())
                .claim(JwtConst.JWT_KEY_EMAIL, jwtInfo.getEmail())
                .claim(JwtConst.JWT_KEY_ROLE, jwtInfo.getUserRole())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    /**
     * 从 token 中获取用户信息
     * 会抛出过期和篡改的异常
     *
     * @param token
     * @return
     */
    public JwtInfo getTokenInfo(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return JwtInfo.builder()
                .id(claims.get(JwtConst.JWT_KEY_UID, Integer.class))
                .email(claims.get(JwtConst.JWT_KEY_EMAIL, String.class))
                .uname(claims.get(JwtConst.JWT_KEY_NAME, String.class))
                .userRole(UserRole.valueOf(claims.get(JwtConst.JWT_KEY_ROLE, String.class)))
                .build();
    }

}