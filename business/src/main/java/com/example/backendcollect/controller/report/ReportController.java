package com.example.backendcollect.controller.report;

import com.example.backendcollect.auth.annotation.Authentication;
import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.po.mongo.ReportSimilarity;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.service.ReportBugService;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.utils.validation.Uid;
import com.example.backendcollect.vo.ResultVO;
import com.example.backendcollect.vo.bug.BugTableVO;
import com.example.backendcollect.vo.report.CriticismVO;
import com.example.backendcollect.vo.report.ReportClusterVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.github.pagehelper.PageInfo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.example.backendcollect.utils.Constant.SIMILAR_REPORT_NUM;


@Validated
@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource
    ReportService reportService;

    @Resource
    ReportBugService reportBugService;

    @Resource
    FileService fileService;

    ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }


    //    @Authentication
    @PostMapping("/submit")
    public ResultVO<ReportVO> submitReport(@RequestParam("file") @NotNull MultipartFile[] pictures, @Valid ReportVO report) {
        StringBuilder picURLs = new StringBuilder(); // 格式是 url;url;url

        for (MultipartFile picture : pictures) {
            picURLs.append(fileService.uploadFile(picture).getUrl());
            picURLs.append(";");
        }
        report.setScreenshot(picURLs.substring(0, picURLs.length() - 1));
        ReportVO reportVO = reportService.submitReport(report);
        return resultHelper.success(reportVO);
    }

    //    @Authentication
    @GetMapping("/getAReport/{reportid}")
    public ResultVO<ReportVO> getAReport(@PathVariable Integer reportid) {
        ReportVO aReport = reportService.getAReport(reportid);
        return resultHelper.success(aReport);
    }

    @GetMapping("/taskAllReports/{taskid}")
    public List<ReportVO> getReportsOfTask(@PathVariable Integer taskid) {
        return reportService.getAllReportOfTask(taskid);
    }


    @Authentication
    @GetMapping("/userTaskReport/{uid}")
    // 不改 api 而是对传入的 uid 校验. 方法: 在自定义的注解校验器中判断 uid 是否与 token 解析出来的 jwtinfo.id 相等.
    public ResultVO<ReportVO> getUserTaskReport(@Uid @PathVariable Integer uid, @RequestParam Integer taskId) {
        ReportVO reportByWorkerAndTask = reportService.getReportByWorkerAndTask(uid, taskId);
        return resultHelper.success(reportByWorkerAndTask);
    }

    @PostMapping("/criticism")
    public ResultVO<CriticismVO> criticizeReport(@RequestBody CriticismVO criticism) {
        // TODO 错误的情况
        CriticismVO criticismVO = reportService.criticizeReport(criticism);
        return resultHelper.success(criticismVO);
    }

    @GetMapping("/sortedAll/{taskId}")
    public PageInfo<ReportVO> getReportsOfTask(@PathVariable Integer taskId, @RequestParam String sortType, @RequestParam boolean reverse, @RequestParam Integer page) {
        if (sortType.equals("Score")) {
            return reportService.getReportsOfTaskByScore(taskId, reverse, page);
        } else if (sortType.equals("Similarity")) {
            //TODO
        }
        return null;
    }

    @GetMapping("/getCriticism/{uid}")
    public ResultVO<CriticismVO> getUserCriticismOfReport(@PathVariable Integer uid, @RequestParam Integer reportId) {
        CriticismVO criticismVO = reportService.getUserCriticismOfReport(uid, reportId);
        return resultHelper.success(criticismVO);
    }

    /**
     * 得到某报告的相似报告
     */
    @GetMapping("/getSimilarList/{reportId}")
    public List<ReportSimilarity.Reports> getSimilarList(@PathVariable Integer reportId) {
        //TODO 需要查看报告是否存在
        return reportService.getSimilarReports(reportId, SIMILAR_REPORT_NUM);
    }

    @GetMapping("/getSimilarityOfTwo")
    public ResultVO<Double> getSimilarityOfTwo(@RequestParam Integer report1, @RequestParam Integer report2) {
        return resultHelper.success(reportService.getSimilarityOfTwo(report1, report2));
    }

    @GetMapping("/getAllScores/{reportId}")
    public List<Integer> getAllScoresOfAReport(@PathVariable Integer reportId) {
        return reportService.getAllScoresOfAReport(reportId);
    }

    @GetMapping("/getAllCriticisms/{reportId}")
    public List<CriticismVO> getAllCriticismsOfAReport(@PathVariable Integer reportId) {
        return reportService.getAllCriticismsOfAReport(reportId);
    }

    @PostMapping("/employerExamReport/{reportId}")
    public ResultVO<String> criticizeReportByEmployer(@PathVariable Integer reportId, @RequestParam Integer taskId, @RequestParam String reportBugType, @RequestParam String bug) {
        BugTableVO bugTableVO;
        switch (reportBugType) {
            case "newBug":
                bugTableVO = reportBugService.submitNewBug(taskId, reportId, bug);
                return resultHelper.success(bugTableVO.getBugName());
            case "oldBug":
                bugTableVO = reportBugService.selectOldBug(taskId, reportId, bug);
                if (bugTableVO!=null)
                    return resultHelper.success(bugTableVO.getBugName());
                else
                    throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
            case "noBug":
                reportBugService.setNotBug(reportId);
                return resultHelper.success();
            default:
                throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
        }
    }

    @GetMapping("/reportClusters/{taskId}")
    public List<ReportClusterVO> getReportClusters(@PathVariable Integer taskId) {
        return reportService.getReportClusters(taskId);
    }

    @GetMapping("/getBugNameById/{bugId}")
    public ResultVO<BugTableVO> getBugNameById(@PathVariable Integer bugId) {
        return resultHelper.success(reportBugService.getBugNameById(bugId));
    }
}
