package com.example.backendcollect.po;


import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.OrderConvertMapper;
import com.example.backendcollect.vo.Order.OrderVO;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode
public class Order {
    private Integer id;

    private Integer workerId;

    private Integer taskId;

    private Date selectTime;

    private Integer orderState;

    public Order() {
    }

    public Order(Order order) {
        BeanCopierWithCacheUtil.copy(order, this);
    }

    public Order(OrderVO orderVO) {
        this(OrderConvertMapper.INSTANCE.vo2po(orderVO));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(Date selectTime) {
        this.selectTime = selectTime;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
}
