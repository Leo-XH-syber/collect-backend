package com.example.backendcollect.vo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class UserFormVO {
    @NotNull(message = "EMPTY_USERNAME")
    private String email;
    @NotNull(message = "EMPTY_PASSWORD")
    private String password;

    public UserFormVO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
