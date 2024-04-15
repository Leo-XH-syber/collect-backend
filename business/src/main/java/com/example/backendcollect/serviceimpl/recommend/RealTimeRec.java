package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.mapperservice.mongo.RealtimeRecsMapper;
import com.example.backendcollect.vo.task.TaskVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.backendcollect.serviceimpl.recommend.Common.getTaskVOS;

@Service
public class RealTimeRec extends RecommendStrategy {
    public RealTimeRec() {
        super.strategyType = RecStrategyType.REAL_TIME;
    }

    @Resource
    RealtimeRecsMapper realtimeRecsMapper;
    @Resource
    TaskMapper taskMapper;

    @Override
    public List<TaskVO> recommend(Integer workId) {
        return getTaskVOS(realtimeRecsMapper.selectByWorkerId(workId), taskMapper, workId);
    }

    @Override
    public List<TaskVO> recommend() {
        return null;
    }
}
