package com.example.backendcollect.auth.pojo;

import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: Rikka
 * @date: 2022/3/1 下午9:48
 * @description 用于 token 的 claim.
 */

@Data
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtInfo {
    // 要注意不能把密码放进 token
    private Integer id;
    private String uname;
    private String email;
    private UserRole userRole;

    public void setJwtInfo(JwtInfo jwtInfo) {
        BeanCopierWithCacheUtil.copy(jwtInfo, this);
    }

    public void setNull() {
        this.id = null;
        this.uname = null;
        this.email = null;
        this.userRole = null;
    }
}
