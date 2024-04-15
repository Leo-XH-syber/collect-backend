package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.po.Order;
import com.example.backendcollect.vo.Order.OrderVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;


/**
 * @author: Rikka
 * @date: 2022/3/4 上午11:19
 * @description
 */
class OrderConvertMapperTest {
    @Test
    public void test() {
        OrderVO order = new OrderVO();
        order.setId(1);
        order.setSelectTime(new Date());
        order.setWorkerId(1);
        order.setTaskId(2);
        order.setOrderState(OrderState.EXPIRED);

        OrderVO orderVO = new OrderVO(new Order(order));
        Assertions.assertEquals(orderVO.getOrderState(), order.getOrderState());
        Assertions.assertEquals(orderVO.getId(), order.getId());
    }

}