package com.example.backendcollect.controller.report;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.service.CoReportService;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.ResultVO;
import com.example.backendcollect.vo.report.CoReportVO;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/collaReport")
public class CoReportController {

    @Resource
    FileService fileService;

    @Resource
    CoReportService coReportService;
    // 从 token 取出的用户信息, 通过注入传到这里
    private JwtInfo jwtInfo;

    ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }

    @Autowired
    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }

    //    @Authentication
    @PostMapping("/submit")
    public ResultVO<CoReportVO> submitCoReport(@RequestParam("file") @NotNull MultipartFile[] pictures, @Valid CoReportVO coReport) {

        //TODO
        StringBuilder picURLs = new StringBuilder(); // 格式是 url;url;url

        for (MultipartFile picture : pictures) {
            picURLs.append(fileService.uploadFile(picture).getUrl());
            picURLs.append(";");
        }
        coReport.setScreenshot(picURLs.substring(0, picURLs.length() - 1));
        CoReportVO reportVO = coReportService.submitReport(coReport);
        return resultHelper.success(reportVO);
    }

    //    @Authentication
    @GetMapping("/getAReport/{collaReportId}")
    public ResultVO<CoReportVO> getACoReport(@PathVariable Integer collaReportId) {
        CoReportVO aReport = coReportService.getAReport(collaReportId);
        return resultHelper.success(aReport);
    }

    @GetMapping("/getList/{beCollaboretedReportId}")
    public List<CoReportVO> getCoReportsOfReport(@PathVariable Integer beCollaboretedReportId) {
        return coReportService.getCoReportsOfReport(beCollaboretedReportId);
    }

    @GetMapping("/getCoReportByUserAndOriginalReport/{workerId}")
    public ResultVO<CoReportVO> getCoReportByUserAndOriginalReport(@PathVariable Integer workerId, @RequestParam Integer originalReportId) {
        CoReportVO aReport = coReportService.getCoReportByUserAndOriginalReport(workerId, originalReportId);
        return resultHelper.success(aReport);
    }

    @PostMapping("/coReportCriticism/{coReportId}")
    public ResultVO<CoReportVO> coReportCriticismByAuthor(@PathVariable Integer coReportId, @RequestParam Integer score) {
        //TODO
        CoReportVO coReportVO = coReportService.criticismByAuthor(coReportId, score);
        return resultHelper.success(coReportVO);
    }
}
