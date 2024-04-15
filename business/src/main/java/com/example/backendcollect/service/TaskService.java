package com.example.backendcollect.service;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.vo.task.TaskVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TaskService {

    // 创建任务
    TaskVO createTask(TaskVO taskVO);

    // 查询发包方所有的
    PageInfo<TaskVO> getTasksByEmployer(Integer employerId, Integer page, Integer taskState);

    // 查询工人所有的
    PageInfo<TaskVO> getTasksByWorker(Integer uid, Integer page, int orderState);

    PageInfo<TaskVO> getAllValidTask(Integer page, Integer uid);
    PageInfo<TaskVO> getAllValidTaskDesc(Integer page, Integer uid);


    PageInfo<TaskVO> getTaskHall(Integer page);

    PageInfo<TaskVO> getAllTask(Integer page);

    TaskVO getATask(Integer taskId);

    List<TaskVO> getRecommendedTask(Integer uid);

    RecStrategyType setRecommendStrategy(RecStrategyType strategy);

    RecStrategyType getRecommendStrategy();

}
