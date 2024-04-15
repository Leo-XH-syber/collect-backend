package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.vo.task.TaskVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rikka
 * @date 2022-03-27 21:46:39
 * @description
 */
@SpringBootTest
class HistoryPopularRecTest {
    @Resource
    HistoryPopularRec historyPopularRec;

    @Test
    void recommend() {
        List<TaskVO> recommend = historyPopularRec.recommend();
//        System.out.println(recommend.get(0));
    }
}