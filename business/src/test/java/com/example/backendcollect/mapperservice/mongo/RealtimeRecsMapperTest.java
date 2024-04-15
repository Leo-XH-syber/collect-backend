package com.example.backendcollect.mapperservice.mongo;

import com.example.backendcollect.po.mongo.WorkerRecs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rikka
 * @date 2022-03-27 17:18:36
 * @description
 */
@SpringBootTest
class RealtimeRecsMapperTest {
    @Resource
    RealtimeRecsMapper realtimeRecsMapper;

    @Test
    void selectByWorkerId() {
        List<WorkerRecs.Rec> recs = realtimeRecsMapper.selectByWorkerId(1);
        System.out.println(recs);
    }
}