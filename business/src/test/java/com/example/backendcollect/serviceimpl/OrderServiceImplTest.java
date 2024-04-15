package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @Mock
    OrderMapper orderMapper;

    @Test
    void queryOrder() {
        MockitoAnnotations.openMocks(this); // 不能少



    }

}