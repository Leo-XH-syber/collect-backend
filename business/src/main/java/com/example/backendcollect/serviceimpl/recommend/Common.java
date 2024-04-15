package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.po.Task;
import com.example.backendcollect.po.mongo.PopularTask;
import com.example.backendcollect.po.mongo.WorkerRecs;
import com.example.backendcollect.vo.task.TaskVO;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rikka
 * @date 2022-03-27 21:14:36
 * @description 方法都长得一样, 做一个公用的方法
 */
public class Common {
    @NotNull
    static List<TaskVO> getTaskVOS(List<WorkerRecs.Rec> recs2, TaskMapper taskMapper, Integer workId) {
        List<Integer> collect = recs2.stream().map(WorkerRecs.Rec::getTaskId).collect(Collectors.toList());
        if (collect.size() == 0) {
            return new ArrayList<>();
        }
        List<Task> tasks = taskMapper.selectIdInSets(collect);
        return tasks.stream().map(TaskVO::new).collect(Collectors.toList());
    }

    @NotNull
    static List<TaskVO> getPopTaskVOS(List<PopularTask> historyPopularTasks, TaskMapper taskMapper) {
        List<Integer> collect = historyPopularTasks.stream().map(PopularTask::getTaskId).collect(Collectors.toList());
        if (collect.size() == 0) {
            return new ArrayList<>();
        }
        List<Task> tasks = taskMapper.selectIdInSets(collect);
        return tasks.stream().map(TaskVO::new).collect(Collectors.toList());
    }
}
