package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.BugTable;

import java.util.List;

public interface BugTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BugTable record);

    BugTable selectByPrimaryKey(Integer id);

    List<BugTable> selectAll();

    int updateByPrimaryKey(BugTable record);

    List<BugTable> selectByTaskId(Integer taskId);
}
