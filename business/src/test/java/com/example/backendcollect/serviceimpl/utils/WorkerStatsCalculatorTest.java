package com.example.backendcollect.serviceimpl.utils;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.mapperservice.*;
import com.example.backendcollect.po.*;
import com.example.backendcollect.utils.Converter.IntegerToEnumConverterFactory;
import com.example.backendcollect.utils.DateUtil;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.yaml.snakeyaml.representer.Represent;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class WorkerStatsCalculatorTest {
    @InjectMocks
    WorkerStatsCalculator workerStatsCalculator=new WorkerStatsCalculator();
    @Mock
    ReportMapper reportMapper;
    @Mock
    OrderMapper orderMapper;
    @Mock
    CoReportMapper coReportMapper;
    @Mock
    CriticismMapper criticismMapper;
    @Mock
    WorkerPropertyMapper workerPropertyMapper;


    @Test
    void getWorkerAllProperty() throws ParseException {
        MockitoAnnotations.openMocks(this);

//        Mockito.when(reportMapper.getBugReportPercentageByWorkerId(1)).thenReturn(20.0);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        Mockito.when(orderMapper.getWorkerTaskKindsByWorkerId(1)).thenReturn(list);
        List<Integer> list2=new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(4);
        Mockito.when(orderMapper.getWorkerOSKindsByWorkerId(1)).thenReturn(list2);
//        CoReport coReport=new CoReport();
//        coReport.setId(122);
//        coReport.setWorkerId(133);
//        coReport.setBeCollaboratedReportId(8);
//        coReport.setTaskId(14);
//        coReport.setCreateTime(new Date());
//        coReport.setDescription("zhangweiqi");
//        coReport.setDeviceOs(1);
//        coReport.setRecoveryStep("what is Grand Theft Auto VI?");
//        coReport.setDeviceType("Redmi K50 Pro");
//        coReport.setScreenshot("https://www.apple.com");
//        coReport.setDifficulty(0);
//        coReport.setScoreFromAuthor(11);
//        CoReport coReport2=new CoReport();
//        coReport2.setId(122);
//        coReport2.setWorkerId(133);
//        coReport2.setBeCollaboratedReportId(8);
//        coReport2.setTaskId(14);
//        coReport2.setCreateTime(new Date());
//        coReport2.setDescription("zhangweiqi");
//        coReport2.setDeviceOs(1);
//        coReport2.setRecoveryStep("what is Grand Theft Auto VI?");
//        coReport2.setDeviceType("Redmi K50 Pro");
//        coReport2.setScreenshot("https://www.apple.com");
//        coReport2.setDifficulty(2);
//        coReport2.setScoreFromAuthor(22);
//        List<CoReport> list3=new ArrayList<>();
//        list3.add(coReport);
//        list3.add(coReport2);
//        Mockito.when(coReportMapper.selectByCoWorkerId(1)).thenReturn(list3);
//        List<Report> list4=new ArrayList<>();
//        for(int i=0;i<2;i++){
//            Report report=new Report();
//            report.setAvg_score(11.0*i);
//            report.setId(1);
//            report.setTaskId(1);
//            report.setWorkerId(2);
//            report.setCreateTime(new Date());
//            report.setDescription("sss");
//            report.setDeviceOs(1);
//            report.setDeviceType("aa");
//            report.setRecoveryStep("aa");
//            report.setScreenshot("aaa");
//            report.setDifficulty(i);
//            list4.add(report);
//        }
//        Mockito.when(reportMapper.selectWithScoreAndDifficultyByWorker(1)).thenReturn(list4);
        List<Criticism> list5=new ArrayList<>();
        list5.add(Criticism.builder()
                .id(100)
                .comments("zhangwei")
                .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                .reportId(1)
                .score(33)
                .workerId(100)
                .build());
        list5.add(Criticism.builder()
                .id(100)
                .comments("zhangwei")
                .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                .reportId(2)
                .score(32)
                .workerId(100)
                .build());
//        Mockito.when(criticismMapper.selectByWorkerId(1)).thenReturn(list5);
//        Mockito.when(criticismMapper.getAvgScoreByReport(1)).thenReturn(11);
//        Mockito.when(criticismMapper.getAvgScoreByReport(2)).thenReturn(22);
        List<Order> orders=new ArrayList<>();
        for(int k=0;k<22;k++)
            orders.add(new Order());
        Mockito.when(orderMapper.selectAllByWorkerId(1)).thenReturn(orders);
        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();


        WorkerProperty workerPrefer=WorkerProperty.builder()
                .workerId(1)
                .activity(10)
                .defaultOs(1)
                .preference("1;2")
                .ability(1.1)
                .build();
        WorkerPropertyVO workerPropertyVO=new WorkerPropertyVO(workerPrefer);
        workerPropertyVO.setAbility(1.1);
        workerPropertyVO.setActivity(10);
        workerPropertyVO.setDefaultOs(OSKind.HarmonyOS);
        TaskType[] taskTypes={TaskType.PERFORMANCE_TEST,TaskType.SAFETY_TEST,TaskType.COMPATIBILITY_TEST};
        workerPropertyVO.setPreference(taskTypes);
        workerPropertyVO.setCooperationAbility(19.25);
        workerPropertyVO.setCriticismAbility(16.0);
        workerPropertyVO.setReportAvgCriticism(0.0);
        workerPropertyVO.setAvgRepeatability(0.0);
        workerPropertyVO.setBugReportProportion(20.0);
        workerPropertyVO.setDuplicateIndex(0.0);
        workerPropertyVO.setSyntheticalScore(6.261166666666667);


        assertEquals(workerPropertyVO,workerStatsCalculator.getWorkerAllProperty(new WorkerProperty(workerPropertyVO)));
    }

    @Test
    void getBugReportPercentage() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(reportMapper.getBugReportPercentageByWorkerId(1)).thenReturn(20.0);
        assertEquals(20.0,workerStatsCalculator.getBugReportPercentage(1));
    }
    @Test
    void getWorkerTaskPreference() {
        MockitoAnnotations.openMocks(this);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        Mockito.when(orderMapper.getWorkerTaskKindsByWorkerId(1)).thenReturn(list);
        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        List<TaskType> taskTypeList=new ArrayList<>();
        taskTypeList.add(TaskType.PERFORMANCE_TEST);
        taskTypeList.add(TaskType.SAFETY_TEST);
        taskTypeList.add(TaskType.COMPATIBILITY_TEST);

        assertEquals(taskTypeList,workerStatsCalculator.getWorkerTaskPreference(1));
    }
    @Test
    void getWorkerPreferOS() {
        MockitoAnnotations.openMocks(this);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        Mockito.when(orderMapper.getWorkerOSKindsByWorkerId(1)).thenReturn(list);
        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        assertEquals(OSKind.HarmonyOS,workerStatsCalculator.getWorkerPreferOS(1));
    }
    @Test
    void getWorkerCooperatingScore() {
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
        coReport.setDifficulty(0);
        coReport.setScoreFromAuthor(11);
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
        coReport2.setDifficulty(2);
        coReport2.setScoreFromAuthor(22);
        List<CoReport> list=new ArrayList<>();
        list.add(coReport);
        list.add(coReport2);

        Mockito.when(coReportMapper.selectByCoWorkerId(1)).thenReturn(list);
        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        assertEquals(19.25,workerStatsCalculator.getWorkerCooperatingScore(1));
    }
    @Test
    void getWorkerProfessionalScore() {
        MockitoAnnotations.openMocks(this);
        List<Report> list=new ArrayList<>();
        for(int i=0;i<2;i++){
            Report report=new Report();
            report.setAvg_score(11.0*i);
            report.setId(1);
            report.setTaskId(1);
            report.setWorkerId(2);
            report.setCreateTime(new Date());
            report.setDescription("sss");
            report.setDeviceOs(1);
            report.setDeviceType("aa");
            report.setRecoveryStep("aa");
            report.setScreenshot("aaa");
            report.setDifficulty(i);
            list.add(report);
        }

        Mockito.when(reportMapper.selectWithScoreAndDifficultyByWorker(1)).thenReturn(list);
        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        assertEquals(7.333333333333333,workerStatsCalculator.getWorkerProfessionalScore(1));
    }
    @Test
    void getWorkerExamScore() throws ParseException {
        MockitoAnnotations.openMocks(this);
        List<Criticism> list=new ArrayList<>();
        list.add(Criticism.builder()
                .id(100)
                .comments("zhangwei")
                .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                .reportId(1)
                .score(33)
                .workerId(100)
                .build());
        list.add(Criticism.builder()
                .id(100)
                .comments("zhangwei")
                .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                .reportId(2)
                .score(32)
                .workerId(100)
                .build());
        Mockito.when(criticismMapper.selectByWorkerId(1)).thenReturn(list);
        Mockito.when(criticismMapper.getAvgScoreByReport(1)).thenReturn(11.0);
        Mockito.when(criticismMapper.getAvgScoreByReport(2)).thenReturn(22.0);

        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        assertEquals(5-16.0,workerStatsCalculator.getWorkerExamScore(1));
    }
    @Test
    void getWorkerBeCriticizedDegree() throws ParseException {
        MockitoAnnotations.openMocks(this);
//        Mockito.when(criticismMapper.getAverageCriticismNumOfWorkersReports(1)).thenReturn(9);
        List<Report> reports=this.getReportListForTest();
        Mockito.when(reportMapper.selectByWorker(1)).thenReturn(reports);
        List<Criticism> list=new ArrayList<>();
        for(int i=0;i<2;i++){
            list.add(Criticism.builder()
                    .id(100+i)
                    .comments("zhangwei")
                    .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                    .reportId(1+i)
                    .score(33)
                    .workerId(100)
                    .build());
        }
        Mockito.when(criticismMapper.selectByReport(1)).thenReturn(list);
        Mockito.when(criticismMapper.selectByReport(2)).thenReturn(list);

        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        assertEquals(2.0,workerStatsCalculator.getWorkerBeCriticizedDegree(1));
    }
    @Test
    void getDuplicateIndexOfWorker() throws ParseException {
        MockitoAnnotations.openMocks(this);
        List<Report> list=new ArrayList<>();
        for(int i=0;i<2;i++){
            Report report=Report.builder()
                    .id(100+i++)
                    .workerId(1)
                    .taskId(1)
                    .screenshot("a")
                    .recoveryStep("a")
                    .deviceType("a")
                    .deviceOs(1)
                    .description("a")
                    .createTime(DateUtil.toDate("2020-12-12 12:12:12"))
                    .bugId(i)
                    .build();
            list.add(report);
        }
        Mockito.when(reportMapper.selectBugReportByWorkerId(1)).thenReturn(list);
        lenient().when(reportMapper.getRepeatNumByBugId(0)).thenReturn(11);
        lenient().when(reportMapper.getRepeatNumByBugId(1)).thenReturn(123);


        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        assertEquals(0.008130081300813009,workerStatsCalculator.getDuplicateIndexOfWorker(1));
    }
    @Test
    void getDuplicatedDegreeOfWorker() throws ParseException {
        MockitoAnnotations.openMocks(this);
        List<Report> list=new ArrayList<>();
        for(int i=0;i<2;i++){
            Report report=Report.builder()
                    .id(100+i++)
                    .workerId(1)
                    .taskId(1)
                    .screenshot("a")
                    .recoveryStep("a")
                    .deviceType("a")
                    .deviceOs(1)
                    .description("a")
                    .createTime(DateUtil.toDate("2020-12-12 12:12:12"))
                    .bugId(i)
                    .build();
            list.add(report);
        }
        Mockito.when(reportMapper.selectBugReportByWorkerId(1)).thenReturn(list);
        lenient().when(reportMapper.getRepeatNumByBugId(0)).thenReturn(11);
        lenient().when(reportMapper.getRepeatNumByBugId(1)).thenReturn(123);


        workerStatsCalculator.integerToEnumConverterFactory=new IntegerToEnumConverterFactory();
        assertEquals(0.008130081300813009,workerStatsCalculator.getDuplicatedDegreeOfWorker(1));
    }

    private List<Report> getReportListForTest(){
        List<Report> list=new ArrayList<>();
        for(int i=0;i<2;i++){
            Report report=new Report();
            report.setAvg_score(11.0*i);
            report.setId(1+i);
            report.setTaskId(1);
            report.setWorkerId(i+1);
            report.setCreateTime(new Date());
            report.setDescription("sss");
            report.setDeviceOs(1);
            report.setDeviceType("aa");
            report.setRecoveryStep("aa");
            report.setScreenshot("aaa");
            report.setDifficulty(i);
            list.add(report);
        }
        return list;
    }




}
