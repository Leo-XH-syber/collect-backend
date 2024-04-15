package com.example.backendcollect.serviceimpl.aspects;

import com.example.backendcollect.mapperservice.ReportMapper;
import com.example.backendcollect.mapperservice.WorkerPropertyMapper;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.service.ReportBugService;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.service.UserService;
import com.example.backendcollect.serviceimpl.utils.WorkerStatsCalculator;
import com.example.backendcollect.vo.Order.OrderVO;
import com.example.backendcollect.vo.report.CoReportVO;
import com.example.backendcollect.vo.report.CriticismVO;
import com.example.backendcollect.vo.report.ReportVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class WorkerStatsAspect {
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    ReportBugService reportBugService;
    @Autowired
    WorkerStatsCalculator workerStatsCalculator;
    @Resource
    WorkerPropertyMapper workerPropertyMapper;
    @Resource
    ReportMapper reportMapper;

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.OrderService.selectTask(..))", returning = "retVal")
    public void updateTaskCount(OrderVO retVal) {
        userService.increaseTaskCount(retVal.getWorkerId());
    }

    //提交新bug时 更新history_bug_count，BugReport占比
    @Before("execution(* com.example.backendcollect.service.ReportBugService.submitNewBug(Integer,Integer,String))")
    public void updateWhenSubmitNewBug(JoinPoint joinPoint) {
        Integer reportId = (Integer) joinPoint.getArgs()[1];
        ReportVO report = reportService.getAReport(reportId);

        WorkerProperty workerProperty = workerPropertyMapper.selectByPrimaryKey(report.getWorkerId());
        workerProperty.setBugReportProportion(workerStatsCalculator.getBugReportPercentage(report.getWorkerId()));
        workerProperty.setHistoryBugCount(workerProperty.getHistoryBugCount()+1);
        workerPropertyMapper.updateByPrimaryKey(workerProperty);

    }

    //    更新BugReport 提交报告
    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.ReportService.submitReport(..))", returning = "retVal")
    public void updateBugReportPercentageWhenSubmit(ReportVO retVal) {
        WorkerProperty workerProperty = workerPropertyMapper.selectByPrimaryKey(retVal.getWorkerId());
        //BugReport占比
        workerProperty.setBugReportProportion(workerStatsCalculator.getBugReportPercentage(retVal.getWorkerId()));
        workerProperty.setAvgRepeatability(workerStatsCalculator.getAverageSimilarity(retVal.getWorkerId()));
        workerPropertyMapper.updateByPrimaryKey(workerProperty);
    }


    //报告协作能力cooperation_ability：协作报告被原作者评价时
    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.CoReportService.criticismByAuthor(..))", returning = "retVal")
    public void updateCooperatingScore(CoReportVO retVal) {
        WorkerProperty workerProperty = workerPropertyMapper.selectByPrimaryKey(retVal.getWorkerId());
        workerProperty.setCooperationAbility(workerStatsCalculator.getWorkerCooperatingScore(retVal.getWorkerId()));
        workerPropertyMapper.updateByPrimaryKey(workerProperty);
    }

    //专业能力ability：该用户的报告均分。用户被评价程度report_avg_criticism。 报告被评价时
    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.ReportService.criticizeReport(..))", returning = "retVal")
    public void updateProfessionalScore(CriticismVO retVal) {
        Report report = reportMapper.selectByPrimaryKey(retVal.getReportId());
        WorkerProperty workerProperty = workerPropertyMapper.selectByPrimaryKey(report.getWorkerId());
        workerProperty.setAbility(workerStatsCalculator.getWorkerProfessionalScore(report.getWorkerId()));
        workerProperty.setReportAvgCriticism(workerStatsCalculator.getWorkerBeCriticizedDegree(report.getWorkerId()));

        workerPropertyMapper.updateByPrimaryKey(workerProperty);
    }

    //用户审查报告能力criticism_ability 用户评价的分数与报告均分的插值. 该评价报告时
    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.ReportService.criticizeReport(..))", returning = "retVal")
    public void updateExamScore(CriticismVO retVal) {
        WorkerProperty workerProperty = workerPropertyMapper.selectByPrimaryKey(retVal.getWorkerId());
        workerProperty.setCriticismAbility(workerStatsCalculator.getWorkerExamScore(retVal.getWorkerId()));
        workerPropertyMapper.updateByPrimaryKey(workerProperty);
    }

    //重复指数avg_repeatability duplicated_index：发包方评价报告时
    @AfterReturning("execution(* com.example.backendcollect.controller.report.ReportController.criticizeReportByEmployer(Integer,Integer,String,String))")
    public void updateDuplicatedIndex(JoinPoint joinPoint) {
        Integer reportId = (Integer) joinPoint.getArgs()[0];
        ReportVO report = reportService.getAReport(reportId);
        WorkerProperty workerProperty = workerPropertyMapper.selectByPrimaryKey(report.getWorkerId());
        workerProperty.setDuplicateIndex(workerStatsCalculator.getDuplicatedDegreeOfWorker(report.getWorkerId()));
//        workerProperty.setAvgRepeatability(workerStatsCalculator.getAverageSimilarity(report.getWorkerId()));

        workerPropertyMapper.updateByPrimaryKey(workerProperty);
    }


}
