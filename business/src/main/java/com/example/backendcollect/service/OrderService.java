package com.example.backendcollect.service;

import com.example.backendcollect.vo.Order.OrderVO;

public interface OrderService {

    OrderVO queryOrder(Integer uid, Integer taskId);

    OrderVO selectTask(Integer uid, Integer taskid);
}
