package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Report record);

    Report selectByPrimaryKey(Integer id);

    List<Report> selectAll();

    int updateByPrimaryKey(Report record);

    Report selectByUserAndTask(@Param(value = "uid") Integer uid,
                               @Param(value = "taskId") Integer taskId);

    List<Report> selectByTask(Integer taskId);

    List<Report> selectByWorker(Integer workerId);

    List<Report> selectByTaskOrderByScoreAsc(Integer taskId);

    List<Report> selectByTaskOrderByScoreDesc(Integer taskId);

    int setBugType(@Param(value = "reportId") Integer reportId,
                   @Param(value = "bugReport") Integer bugReport,
                   @Param(value = "bugID") Integer bugID);

    Double getBugReportPercentageByWorkerId(Integer workerId);

    List<Report> selectWithScoreAndDifficultyByWorker(Integer workerId);

    List<Report> selectBugReportByWorkerId(Integer workerId);

    //得到所有与这个bug重复的报告数量
    Integer getRepeatNumByBugId(Integer bugId);
}
