package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.po.User;
import com.example.backendcollect.vo.user.UserVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Rikka
 * @date: 2022/2/27 下午6:13
 * @description
 */
class UserConvertMapperTest {
    @Test
    public void converter(){
        User user = new User();
        user.setEmail("123@123.com");
        user.setUserRole(0);
        user.setPassword("123123");
        user.setUname("rikka");
        user.setId(1);

        User newUser = new User(new UserVO(user));
        assertEquals(newUser.getId(), user.getId());
        assertEquals(newUser.getUserRole(), user.getUserRole());
        assertNull(newUser.getPassword());

    }

}