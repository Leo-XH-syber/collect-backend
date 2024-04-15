package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.Criticism;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CriticismMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Criticism record);

    Criticism selectByPrimaryKey(Integer id);

    List<Criticism> selectAll();

    int updateByPrimaryKey(Criticism record);

    //某用户对某报告的评价
    Criticism selectByUserAndReport(@Param(value = "workerId") Integer uid,
                                    @Param(value = "reportId") Integer reportId);

    //某报告的所有评价
    List<Criticism> selectByReport(Integer reportId);

    //某报告的平均分
    double getAvgScoreByReport(Integer reportId);

    //某用户发表的评价
    List<Criticism> selectByWorkerId(Integer workerId);

    //某用户发表的report，平均被几个人评价过
    Integer getAverageCriticismNumOfWorkersReports(Integer workerId);
    //某用户发表的report被多少个评价了
}
