package com.example.backendcollect.vo.Order;

import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.po.Order;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.OrderConvertMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderVO {

    private Integer id;

    private Integer workerId;

    private Integer taskId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date selectTime;

    private OrderState orderState;

    public OrderVO(OrderVO orderVO) {
        BeanCopierWithCacheUtil.copy(orderVO, this);
    }

    public OrderVO(@NonNull Order order) {
        this(OrderConvertMapper.INSTANCE.po2Vo(order));
    }

}
