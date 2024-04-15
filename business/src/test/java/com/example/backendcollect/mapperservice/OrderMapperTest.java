package com.example.backendcollect.mapperservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Rikka
 * @date: 2022/3/4 下午8:24
 * @description
 */
@SpringBootTest
class OrderMapperTest {

    @Resource
    OrderMapper orderMapper;

    @Test
    void updateOrderState() {
        int i = orderMapper.updateOrderState(1, 1, 2);
        System.out.println(i);
    }

    @Test
    @Transactional
    void getWorkerTaskKindsByWorkerIdTest(){
        List<Integer> kinds=new ArrayList<>();
        kinds.add(1);
        kinds.add(2);
        kinds.add(3);
//        Assertions.assertEquals(kinds,orderMapper.getWorkerTaskKindsByWorkerId(1));
    }
    @Test
    @Transactional
    void getWorkerOSKindsByWorkerId(){
        List<Integer> kinds=new ArrayList<>();
        kinds.add(1);
        kinds.add(2);
        kinds.add(3);
//        Assertions.assertEquals(kinds,orderMapper.getWorkerTaskKindsByWorkerId(1));

    }


}
