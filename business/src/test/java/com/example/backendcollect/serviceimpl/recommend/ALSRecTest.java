package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.vo.task.TaskVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rikka
 * @date 2022-03-27 21:35:04
 * @description
 */
@SpringBootTest
class ALSRecTest {

    @Resource
    ALSRec alsRec;
    @Test
    void recommend() {
        List<TaskVO> recommend = alsRec.recommend(0);
        System.out.println(recommend);
    }
}