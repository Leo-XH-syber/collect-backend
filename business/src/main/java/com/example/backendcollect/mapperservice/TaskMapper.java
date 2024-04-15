package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);

    List<Task> selectByEmployerId(@Param(value = "uid") Integer uid, @Param(value = "taskState") Integer taskState);

    List<Task> selectAllValid();
    List<Task> selectAllValidDesc();

    List<Task> selectIdInSets(@Param(value = "ids") List<Integer> ids);

    List<Task> selectByTaskKind(Integer kind, Integer limit);

}