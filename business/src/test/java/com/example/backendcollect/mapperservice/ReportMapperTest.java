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

/**
 * @author: Rikka
 * @date: 2022/3/4 下午8:23
 * @description
 */


// 需要 spring 环境加这个
@SpringBootTest
//@RunWith(SpringRunner.class)
@MybatisTest //这个是启用自己配置的数据元，不加则采用虚拟数据源
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ReportMapperTest {

    @Resource
    ReportMapper reportMapper;
    @Resource
    CriticismMapper criticismMapper;

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void insert() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void selectAll() {
    }

    @Test
    void updateByPrimaryKey() {
    }

    @Test
    void selectByUserAndTask() {
    }

    @Test
    void selectByTask() {
    }

    @Test
    void selectByWorker() {
    }
    @Test
    @Rollback
    public void selectByTaskOrderByScoreAscTest() throws ParseException {
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
        reports.get(0).setAvg_score(33.0);
        reports.get(1).setAvg_score(53.0);
        reports.get(2).setAvg_score(73.0);
        reports.get(3).setAvg_score(93.0);
        reports.get(4).setAvg_score(113.0);

    }
    @Test
    @Rollback
    public void selectByTaskOrderByScoreDescTest() throws ParseException {
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
                    .bugId(1)
                    .bugReport(1)
                    .createTime(DateUtil.toDate("2020-12-12 12:12:12"))
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
        reports.get(0).setAvg_score(33.0);
        reports.get(1).setAvg_score(53.0);
        reports.get(2).setAvg_score(73.0);
        reports.get(3).setAvg_score(93.0);
        reports.get(4).setAvg_score(113.0);
        Collections.reverse(reports);
//        Assertions.assertEquals(reports,reportMapper.selectByTaskOrderByScoreDesc(1));
    }
    @Test
    @Rollback
    public void getBugReportPercentageByWorkerIdTest() throws ParseException {
        insertReportsByWorker1BeforeTest();
//        reportMapper.getBugReportPercentageByWorkerId(1);

    }
    @Test
    @Rollback
    public void selectByCoWorkerIdTest() throws ParseException{
        insertReportsByWorker1BeforeTest();
        Assertions.assertEquals(0,reportMapper.selectWithScoreAndDifficultyByWorker(1).size());
    }
    @Test
    @Rollback
    public void selectWithScoreAndDifficultyByWorker() throws ParseException{
        insertReportsByWorker1BeforeTest();
        Assertions.assertEquals(0,reportMapper.selectBugReportByWorkerId(1).size());

    }
    @Test
    @Rollback
    public void selectBugReportByWorkerId() throws ParseException{
        insertReportsByWorker1BeforeTest();
        Assertions.assertEquals(0,reportMapper.selectBugReportByWorkerId(1).size());

    }
    @Test
    @Rollback
    public void getRepeatNumByBugId() throws ParseException{
        insertReportsByWorker1BeforeTest();
        Assertions.assertEquals(5,reportMapper.getRepeatNumByBugId(1));
    }
    private List<Report> insertReportsByWorker1BeforeTest(){
        List<Report> reports=new ArrayList<>();
        int cnt=0;
        for(int r=0;r<5;r++){
            Report report= null;
            try {
                report = Report.builder()
                        .id(100+cnt++)
                        .workerId(1)
                        .taskId(1)
                        .screenshot("a")
                        .recoveryStep("a")
                        .deviceType("a")
                        .deviceOs(1)
                        .description("a")
                        .createTime(DateUtil.toDate("2020-12-12 12:12:12"))
                        .bugReport(2)
                        .bugId(1)
                        .build();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Assertions.assertEquals(1,reportMapper.insert(report));
            reports.add(report);
        }
        return reports;
    }
}
