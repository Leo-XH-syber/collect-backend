package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.po.User;
import com.example.backendcollect.vo.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author: Rikka
 * @date: 2022/2/27 下午12:40
 * @description User2UserVO 无需手动实现
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserConvertMapper {

    UserConvertMapper INSTANCE = Mappers.getMapper(UserConvertMapper.class);

    @Mappings({
            @Mapping(target = "password", ignore = true),
    })
    UserVO po2vo(User userPO);


    @Mappings({
            @Mapping(target = "userRole", expression = "java(userVO.getUserRole().ordinal())"),
    })
    User vo2po(UserVO userVO);

    default UserRole map(Integer value) {
        return UserRole.values()[value];
    }

}
