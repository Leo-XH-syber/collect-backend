package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.utils.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@MybatisTest
//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CriticismMapperTest {
    @Resource
    private CriticismMapper criticismMapper;
    @Resource
    private ReportMapper reportMapper;

    @Test
    @Rollback
    public void insertTest() throws ParseException {
        Criticism criticism= Criticism.builder()
                .id(100)
                .comments("zhangwei")
                .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                .reportId(100)
                .score(33)
                .workerId(100)
                .build();
        Assertions.assertEquals(1,criticismMapper.insert(criticism));

    }

    @Test
    @Rollback
    public void selectByUserAndReportTest() throws ParseException {
        Criticism criticism=Criticism.builder()
                .id(100)
                .comments("zhangwei")
                .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                .reportId(100)
                .score(33)
                .workerId(100)
                .build();
        Assertions.assertEquals(1,criticismMapper.insert(criticism));
        Assertions.assertEquals(criticism,criticismMapper.selectByUserAndReport(100,100));
    }
    @Test
    @Rollback
    public void selectByReportTest() throws ParseException {
        List<Criticism> criticisms=new ArrayList<>();
        for(int i=0;i<5;i++){
            Criticism criticism=Criticism.builder()
                    .id(100+i)
                    .comments("zhangwei")
                    .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                    .reportId(100)
                    .score(33+i)
                    .workerId(100+i)
                    .build();
            Assertions.assertEquals(1,criticismMapper.insert(criticism));
            criticisms.add(criticism);
        }
        Assertions.assertEquals(criticisms,criticismMapper.selectByReport(100));
    }
    @Test
    @Rollback
    public void getAvgScoreByReportTest() throws ParseException {
        List<Criticism> criticisms=new ArrayList<>();
        int avgScore=0;
        int criticismNum=10;
        for(int i=0;i<criticismNum;i++){
            Criticism criticism=Criticism.builder()
                    .id(100+i)
                    .comments("zhangwei")
                    .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                    .reportId(100)
                    .score(33+i)
                    .workerId(100)
                    .build();
            Assertions.assertEquals(1,criticismMapper.insert(criticism));
            avgScore+=33+i;
        }
        avgScore=avgScore/criticismNum;
        Assertions.assertEquals(avgScore,criticismMapper.getAvgScoreByReport(100));

    }
    @Test
    @Rollback
    public void selectByWorkerIdTest() throws ParseException {
        Criticism criticism=Criticism.builder()
                .id(100)
                .comments("zhangwei")
                .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                .reportId(100)
                .score(33)
                .workerId(100)
                .build();
        Assertions.assertEquals(1,criticismMapper.insert(criticism));
        Assertions.assertEquals(Collections.singletonList(criticism),criticismMapper.selectByWorkerId(100));
    }

    @Test
    @Rollback
    public void getAverageCriticismNumOfWorkersReports() throws ParseException {
        List<Report> reports=new ArrayList<>();
        int cnt=0;
        for(int r=0;r<5;r++){
            Report report=Report.builder()
                    .id(100+cnt++)
                    .workerId(1)
                    .taskId(1)
                    .screenshot("a")
                    .recoveryStep("a")
                    .deviceType("a")
                    .deviceOs(1)
                    .description("a")
                    .createTime(DateUtil.toDate("2020-12-12 12:12:12"))
                    .bugReport(1)
                    .bugId(1)
                    .build();
            Assertions.assertEquals(1,reportMapper.insert(report));
            reports.add(report);
            for(int i=0;i<10;i++){
                Criticism criticism=Criticism.builder()
                        .id(100+cnt++)
                        .comments("zhangwei")
                        .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                        .reportId(100+(cnt-2))
                        .score(33+i+r*20)
                        .workerId(100+i)
                        .build();
                Assertions.assertEquals(1,criticismMapper.insert(criticism));
            }
        }
        Assertions.assertEquals(1,criticismMapper.getAverageCriticismNumOfWorkersReports(1));
    }
}
