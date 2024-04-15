package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.mapperservice.mongo.PopularTasksMapper;
import com.example.backendcollect.po.mongo.PopularTask;
import com.example.backendcollect.vo.task.TaskVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.backendcollect.serviceimpl.recommend.Common.getPopTaskVOS;
import static com.example.backendcollect.utils.Constant.POPULAR_TASK_NUM;

@Service
public class MonthPopularRec extends RecommendStrategy {
    public MonthPopularRec() {
        super.strategyType = RecStrategyType.MONTH_POPULAR;
    }

    @Resource
    PopularTasksMapper popularTasksMapper;
    @Resource
    TaskMapper taskMapper;

    @Override
    public List<TaskVO> recommend(Integer workId) {
        List<PopularTask> monthPopularTasks = popularTasksMapper.getRecentPopularTasks(POPULAR_TASK_NUM);
        return getPopTaskVOS(monthPopularTasks, taskMapper);
    }

    @Override
    public List<TaskVO> recommend() {
        List<PopularTask> monthPopularTasks = popularTasksMapper.getRecentPopularTasks(POPULAR_TASK_NUM);
        return getPopTaskVOS(monthPopularTasks, taskMapper);
    }
}
