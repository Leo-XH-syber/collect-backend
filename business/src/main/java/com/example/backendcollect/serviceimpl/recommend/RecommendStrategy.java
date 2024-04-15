package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.vo.task.TaskVO;

import java.util.List;

public abstract class RecommendStrategy {
    public RecStrategyType strategyType;

    public abstract List<TaskVO> recommend(Integer workId);

    public abstract List<TaskVO> recommend();
}
