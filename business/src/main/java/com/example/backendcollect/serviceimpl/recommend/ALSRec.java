package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.mapperservice.mongo.WorkerRecsMapper;
import com.example.backendcollect.vo.task.TaskVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ALSRec extends RecommendStrategy {

    @Resource
    TaskMapper taskMapper;

    @Resource
    WorkerRecsMapper workerRecsMapper;

    public ALSRec() {
        super.strategyType = RecStrategyType.ALS;
    }

    @Override
    public List<TaskVO> recommend(Integer workId) {
        return Common.getTaskVOS(workerRecsMapper.selectByWorkerId(workId), taskMapper, workId);
    }

    @Override
    public List<TaskVO> recommend() {
        return null;
    }

}
