package com.example.backendcollect.mapperservice.mongo;

import com.example.backendcollect.po.mongo.PopularTask;
import com.example.backendcollect.po.mongo.WorkerRecs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author Rikka
 * @date 2022-03-27 03:31:53
 * @description 测试 mongo 是否连接正常
 */

@SpringBootTest
class WorkerRecsMapperTest {
    @Resource
    private WorkerRecsMapper workerRecsMapper;
    @Resource
    private PopularTasksMapper popularTasksMapper;

    @Test
    void selectByWorkerId() {
        List<WorkerRecs.Rec> recs = workerRecsMapper.selectByWorkerId(1);
//        System.out.println(recs);
    }

    @Test
    void selectAll() {
        List<WorkerRecs> workerRecs = workerRecsMapper.selectAll();
//        System.out.println(workerRecs);
    }
    @Test
    void selectPopularTasks(){
//        List<PopularTask> historyPopularTasks = popularTasksMapper.getHistoryPopularTasks();
//        List<PopularTask> recentPopularTasks = popularTasksMapper.getRecentPopularTasks();
//        System.out.println(historyPopularTasks);
//        System.out.println(recentPopularTasks);
    }
}