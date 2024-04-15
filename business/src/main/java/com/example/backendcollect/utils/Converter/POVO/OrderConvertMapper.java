package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.po.Order;
import com.example.backendcollect.vo.Order.OrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, imports = {OrderState.class})
public interface OrderConvertMapper {

    OrderConvertMapper INSTANCE = Mappers.getMapper(OrderConvertMapper.class);

    @Mappings({
            @Mapping(target = "orderState", expression = "java(OrderState.values()[order.getOrderState()])")
    })
    OrderVO po2Vo(Order order);

    @Mappings({
            @Mapping(target = "orderState", expression = "java(orderVO.getOrderState().ordinal())")
    })
    Order vo2po(OrderVO orderVO);
}
