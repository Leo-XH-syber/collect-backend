package com.example.backendcollect.vo.user;

import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.po.User;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.UserConvertMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private Integer id;

    @NotNull(message = "EMPTY_USERNAME")
    private String uname;

    @NotNull(message = "EMPTY_EMAIL")
    @Email(message = "EMAIL_FORMAT_ERROR")
    private String email;

    @NotNull(message = "EMPTY_PASSWORD")
    @Size(min = 6, max = 16, message = "PASSWORD_FORMAT_ERROR")
    private String password;

    private UserRole userRole;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public UserVO(UserVO userVO) {
        BeanCopierWithCacheUtil.copy(userVO, this);
    }

    public UserVO(@NonNull User user) {
        // 走了两步, 第一步 mapstruct 转换, 第二步拷贝构造, 我想可以在用的地方自己转换, 但是又得重写代码, 麻烦
        // 反正BeanCopier效率很高, 也就没关系了吧
        // 其实只用一个 mapstruct 完全够了....
        this(UserConvertMapper.INSTANCE.po2vo(user));
    }

}
