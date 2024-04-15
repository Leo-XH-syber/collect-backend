package com.example.backendcollect.service;

import com.example.backendcollect.vo.report.CoReportVO;

import java.util.List;

public interface CoReportService {

    CoReportVO submitReport(CoReportVO coReport);

    CoReportVO getAReport(Integer coReportId);

    List<CoReportVO> getCoReportsOfReport(Integer originReportId);

    CoReportVO getCoReportByUserAndOriginalReport(Integer workerId, Integer originalReportId);

    CoReportVO criticismByAuthor(Integer coReportId, Integer score);
}
