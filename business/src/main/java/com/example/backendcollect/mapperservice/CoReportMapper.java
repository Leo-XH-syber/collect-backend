package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.CoReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoReportMapper {
    int deleteAll();

    int deleteByPrimaryKey(Integer id);

    int insert(CoReport record);

    CoReport selectByPrimaryKey(Integer id);

    List<CoReport> selectAll();

    int updateByPrimaryKey(CoReport record);

    List<CoReport> selectByOriginReport(Integer originReportId);

    CoReport selectByWorkerAndOriginalReport(@Param(value = "workerId") Integer workerId,
                                             @Param(value = "originReportId") Integer originReportId);

    int criticismByAuthor(@Param(value = "coReportId") Integer coReportId,
                          @Param(value = "score") Integer score);

    List<CoReport> selectByCoWorkerId(Integer workerId);
}
