package com.example.backendcollect.service;

import com.example.backendcollect.po.mongo.ReportSimilarity;
import com.example.backendcollect.vo.report.CriticismVO;
import com.example.backendcollect.vo.report.ReportClusterVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ReportService {

    List<ReportVO> getAllReportOfTask(int taskId);

    ReportVO getAReport(Integer reportId);

    ReportVO submitReport(ReportVO report);

    ReportVO getReportByWorkerAndTask(int workerId, int taskId);

    List<ReportVO> getAllReportByWorker(int workerId);

    CriticismVO criticizeReport(CriticismVO criticismVO);

    CriticismVO getUserCriticismOfReport(int uid, int reportId);

    List<CriticismVO> getAllCriticismsOfAReport(int reportId);

    List<Integer> getAllScoresOfAReport(int reportId);

    PageInfo<ReportVO> getReportsOfTaskByScore(Integer taskId, boolean reverse, Integer page);

    List<ReportSimilarity.Reports> getSimilarReports(Integer reportId, Integer num);

    Double getSimilarityOfTwo(Integer report1, Integer report2);

    List<ReportClusterVO> getReportClusters(Integer taskId);
}
