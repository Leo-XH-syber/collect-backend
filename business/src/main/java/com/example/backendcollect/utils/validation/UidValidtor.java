package com.example.backendcollect.utils.validation;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.enums.types.UserRole;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: Rikka
 * @date: 2022/3/4 下午4:17
 * @description 验证器, 如果请求的路径和 uid 一致或者是管理员则通过
 */

public class UidValidtor implements ConstraintValidator<Uid, Integer> {
    private JwtInfo jwtInfo;

    @Autowired
    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }

    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value.equals(jwtInfo.getId()) || jwtInfo.getUserRole().equals(UserRole.ADMIN);
    }
}
