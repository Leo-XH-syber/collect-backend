package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.mapperservice.BugTableMapper;
import com.example.backendcollect.mapperservice.CriticismMapper;
import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.mapperservice.ReportMapper;
import com.example.backendcollect.po.BugTable;
import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.utils.Constant;
import com.example.backendcollect.utils.PageInfoUtil;
import com.example.backendcollect.vo.bug.BugTableVO;
import com.example.backendcollect.vo.report.CriticismVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
class ReportServiceImplTest {
    @InjectMocks
    ReportService reportService=new ReportServiceImpl();
    @Mock
    ReportMapper reportMapper;
    @Mock
    OrderMapper orderMapper;
    @Mock
    CriticismMapper criticismMapper;

    @Test
    void getAllReportOfTask() {
    }

    @Test
    void getReportByWorkerAndTask() {
    }

    @Test
    void getAReport() {
    }

    @Test
    void submitReport() {
    }

    @Test
    void getAllReportByWorker() {
    }

    @Test
    void criticizeReport() {
        MockitoAnnotations.openMocks(this);
        given(criticismMapper.insert(any(Criticism.class))).willReturn(1);
        CriticismVO criticismVO=new CriticismVO();
        criticismVO.setId(1);
        criticismVO.setComments("dsfsf");
        criticismVO.setCreateTime(new Date());
        criticismVO.setReportId(1);
        criticismVO.setScore(11);
        criticismVO.setWorkerId(2);
        CriticismVO result= reportService.criticizeReport(criticismVO);
        assertEquals(criticismVO,result);
    }

    @Test
    void getUserCriticismOfReport() {
        MockitoAnnotations.openMocks(this);
        Criticism criticism=new Criticism();
        criticism.setId(1);
        criticism.setComments("zhangweiqi");
        criticism.setCreateTime(new Date());
        criticism.setReportId(1);
        criticism.setScore(11);
        criticism.setWorkerId(2);
        Mockito.when(criticismMapper.selectByUserAndReport(2,1)).thenReturn(criticism);
        assertEquals(new CriticismVO(criticism),reportService.getUserCriticismOfReport(2,1));
    }

    @Test
    void getAllCriticismsOfAReport() {
        MockitoAnnotations.openMocks(this);
        List<Criticism> criticisms=new ArrayList<>();
        for(int i=0;i<10;i++){
            Criticism criticism=new Criticism();
            criticism.setId(1);
            criticism.setComments("p16sdf"+i);
            criticism.setCreateTime(new Date());
            criticism.setReportId(1);
            criticism.setScore(11+i*12);
            criticism.setWorkerId(2+i);
            criticisms.add(criticism);
        }


        Mockito.when(criticismMapper.selectByReport(1)).thenReturn(criticisms);
        assertEquals(this.convert(criticisms,CriticismVO.class),reportService.getAllCriticismsOfAReport(1));
    }

    @Test
    void getAllScoresOfAReport() {
        MockitoAnnotations.openMocks(this);
        List<Criticism> criticisms=new ArrayList<>();
        List<Integer> scores=new ArrayList<>();
        for(int i=0;i<10;i++){
            Criticism criticism=new Criticism();
            criticism.setId(1);
            criticism.setComments("p16sdf"+i);
            criticism.setCreateTime(new Date());
            criticism.setReportId(1);
            criticism.setScore(11+i*12);
            criticism.setWorkerId(2+i);
            criticisms.add(criticism);
            scores.add(criticism.getScore());
        }

        Mockito.when(criticismMapper.selectByReport(1)).thenReturn(criticisms);
        assertEquals(scores,reportService.getAllScoresOfAReport(1));
    }

    @Test
    void getReportsOfTaskByScore() {
        MockitoAnnotations.openMocks(this);
        List<Report> reports=new ArrayList<>();
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
            reports.add(report);
        }

        PageInfo<Report> pageInfo=new PageInfo(reports);
        PageHelper.startPage(1, Constant.PAGE_SIZE);
        PageInfo<ReportVO> pages = PageInfoUtil.convert(pageInfo, ReportVO.class);
        Mockito.when(reportMapper.selectByTaskOrderByScoreAsc(1)).thenReturn(reports);
        for(int i=0;i<pages.getList().size();i++){
            assertEquals(pages.getList().get(i),convert(reports,ReportVO.class).get(i));
        }

        Collections.reverse(reports);
        pageInfo=new PageInfo(reports);
        PageHelper.startPage(1, Constant.PAGE_SIZE);
        pages = PageInfoUtil.convert(pageInfo, ReportVO.class);
        Mockito.when(reportMapper.selectByTaskOrderByScoreDesc(1)).thenReturn(reports);
        for(int i=0;i<pages.getList().size();i++){
            assertEquals(pages.getList().get(i),convert(reports,ReportVO.class).get(i));
        }

    }
    @Test
    void getReportClustersTest(){
        //TODO
    }

    private <P, V> List<V> convert(List<P> pos, Class<V> vClass) {
        List<V> vos=new ArrayList<>();
        try {
            if (pos.size() > 0) {
                Constructor<V> constructor = vClass.getConstructor(pos.get(0).getClass());
                for (P p : pos) {
                    V v = null;
                    try {
                        v = constructor.newInstance(p);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    vos.add(v);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return vos;
    }
}
