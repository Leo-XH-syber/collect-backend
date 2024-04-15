package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.mapperservice.mongo.PopularTasksMapper;
import com.example.backendcollect.po.mongo.PopularTask;
import com.example.backendcollect.vo.task.TaskVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.backendcollect.utils.Constant.POPULAR_TASK_NUM;

@Service
public class HistoryPopularRec extends RecommendStrategy {
    @Resource
    PopularTasksMapper popularTasksMapper;
    @Resource
    TaskMapper taskMapper;

    public HistoryPopularRec() {
        super.strategyType = RecStrategyType.HISTORY_POPULAR;
    }

    @Override
    public List<TaskVO> recommend(Integer workId) {
        List<PopularTask> historyPopularTasks = popularTasksMapper.getHistoryPopularTasks(POPULAR_TASK_NUM);
        return Common.getPopTaskVOS(historyPopularTasks, taskMapper);
    }

    @Override
    public List<TaskVO> recommend() {
        List<PopularTask> historyPopularTasks = popularTasksMapper.getHistoryPopularTasks(POPULAR_TASK_NUM);
        return Common.getPopTaskVOS(historyPopularTasks, taskMapper);
    }

}
