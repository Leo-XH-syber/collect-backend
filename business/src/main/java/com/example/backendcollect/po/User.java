package com.example.backendcollect.po;

import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.UserConvertMapper;
import com.example.backendcollect.vo.user.UserVO;

import java.util.Date;

public class User {
    private Integer id;

    private String uname;

    private String email;

    private String password;

    private Integer userRole;

    private Date createTime;

    public User() {
    }

    public User(User user) {
        BeanCopierWithCacheUtil.copy(user, this);
    }

    public User(UserVO userVO) {
        this(UserConvertMapper.INSTANCE.vo2po(userVO));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}