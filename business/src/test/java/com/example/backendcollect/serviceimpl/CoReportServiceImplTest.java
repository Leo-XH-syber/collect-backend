package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.mapperservice.CoReportMapper;
import com.example.backendcollect.po.CoReport;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.service.CoReportService;
import com.example.backendcollect.vo.report.CoReportVO;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CoReportServiceImplTest {
    @InjectMocks
    CoReportService coReportService=new CoReportServiceImpl();
    @Mock
    CoReportMapper coReportMapper;

    @Test
    void submitReport() {
        MockitoAnnotations.openMocks(this);
        CoReportVO coReportVO=new CoReportVO();
        coReportVO.setId(1);
        coReportVO.setWorkerId(133);
        coReportVO.setBeCollaboratedReportId(8);
        coReportVO.setTaskId(14);
        coReportVO.setCreateTime(new Date());
        coReportVO.setDescription("zhangweiqi");
        coReportVO.setScoreFromAuthor(0);
        coReportVO.setDeviceOs(OSKind.iOS);
        coReportVO.setRecoveryStep("what is Grand Theft Auto VI?");
        coReportVO.setDeviceType("Redmi K50 Pro");
        Mockito.when(coReportMapper.insert(any(CoReport.class))).thenReturn(1);
        assertEquals(coReportVO,coReportService.submitReport(coReportVO));
    }

    @Test
    void getAReport() {
        MockitoAnnotations.openMocks(this);
        CoReport coReport=new CoReport();
        coReport.setId(122);
        coReport.setWorkerId(133);
        coReport.setBeCollaboratedReportId(8);
        coReport.setTaskId(14);
        coReport.setCreateTime(new Date());
        coReport.setDescription("zhangweiqi");
        coReport.setDeviceOs(1);
        coReport.setRecoveryStep("what is Grand Theft Auto VI?");
        coReport.setDeviceType("Redmi K50 Pro");
        coReport.setScreenshot("https://www.apple.com");
        Mockito.when(coReportMapper.selectByPrimaryKey(122)).thenReturn(coReport);
        assertEquals(new CoReportVO(coReport),coReportService.getAReport(122));
    }

    @Test
    void getCoReportsOfReport() {
        MockitoAnnotations.openMocks(this);
        CoReport coReport=new CoReport();
        coReport.setId(122);
        coReport.setWorkerId(133);
        coReport.setBeCollaboratedReportId(8);
        coReport.setTaskId(14);
        coReport.setCreateTime(new Date());
        coReport.setDescription("zhangweiqi");
        coReport.setDeviceOs(1);
        coReport.setRecoveryStep("what is Grand Theft Auto VI?");
        coReport.setDeviceType("Redmi K50 Pro");
        coReport.setScreenshot("https://www.apple.com");
        CoReport coReport2=new CoReport();
        coReport2.setId(122);
        coReport2.setWorkerId(133);
        coReport2.setBeCollaboratedReportId(8);
        coReport2.setTaskId(14);
        coReport2.setCreateTime(new Date());
        coReport2.setDescription("zhangweiqi");
        coReport2.setDeviceOs(1);
        coReport2.setRecoveryStep("what is Grand Theft Auto VI?");
        coReport2.setDeviceType("Redmi K50 Pro");
        coReport2.setScreenshot("https://www.apple.com");

        List<CoReport> expect=new ArrayList<>();
        expect.add(coReport);
        expect.add(coReport2);

        List<CoReportVO> expectVOs=new ArrayList<>();
        expectVOs.add(new CoReportVO(coReport));
        expectVOs.add(new CoReportVO(coReport2));

        Mockito.when(coReportMapper.selectByOriginReport(8)).thenReturn(expect);

        Assertions.assertEquals(expectVOs,coReportService.getCoReportsOfReport(8));
    }
}