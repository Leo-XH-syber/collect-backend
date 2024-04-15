package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.WorkerProperty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkerPropertyMapper {
    int deleteByPrimaryKey(Integer workerId);

    int insert(WorkerProperty record);

    WorkerProperty selectByPrimaryKey(Integer workerId);

    List<WorkerProperty> selectAll();

    int updateByPrimaryKey(WorkerProperty record);

    int updateActivityByWorkerIdInt(@Param(value = "workerId") Integer workerId, @Param(value = "activity") Integer activity);

    Double getWorkerAbility(Integer workerId);

    int updateTaskCountByWorkerId(Integer workerId);

    int updateBugReportCountByWorkerId(Integer workerId);
}
