package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.CoReport;
import com.example.backendcollect.utils.DateUtil;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import static org.junit.jupiter.api.Assertions.*;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
//@RunWith(SpringRunner.class)
@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CoReportMapperTest {
    @Resource
    private CoReportMapper coReportMapper;

    @BeforeClass
    public static void before() {

    }
    @Test
    @Rollback
    public void insertTest() throws ParseException {
        CoReport coReport=CoReport.builder()
                .id(10)
                .beCollaboratedReportId(1)
                .createTime(DateUtil.toDate("1999-09-09 12:12:12"))
                .description("zhang")
                .deviceOs(1)
                .deviceType("iPhone 14 Pro Max")
                .screenshot("sf")
                .scoreFromAuthor(1)
                .recoveryStep("ss")
                .workerId(1)
                .taskId(1)
                .build();
        Assertions.assertEquals(1, coReportMapper.insert(coReport));
    }
    @Test
    @Rollback
    public void selectByPrimaryKeyTest() throws ParseException {
        CoReport coReport=CoReport.builder()
                .id(10)
                .beCollaboratedReportId(1)
                .createTime(DateUtil.toDate("1999-09-09 12:12:12"))
                .description("zhang")
                .deviceOs(1)
                .deviceType("iPhone 14 Pro Max")
                .screenshot("sf")
                .recoveryStep("ss")
                .scoreFromAuthor(1)
                .workerId(1)
                .taskId(1)
                .build();
        Assertions.assertEquals(1, coReportMapper.insert(coReport));
        CoReport actual=coReportMapper.selectByPrimaryKey(10);
        assertEquals(coReport, actual);
    }
    @Test
    @Rollback
    public void updateByPrimaryKeyTest() throws ParseException {
        CoReport coReport=CoReport.builder()
                .id(10)
                .beCollaboratedReportId(1)
                .createTime(DateUtil.toDate("1999-09-09 12:12:12"))
                .description("zhang")
                .deviceOs(1)
                .deviceType("iPhone 14 Pro Max")
                .screenshot("sf")
                .recoveryStep("ss")
                .scoreFromAuthor(1)
                .workerId(1)
                .taskId(1)
                .build();
        CoReport newCoReport=CoReport.builder()
                .id(10)
                .beCollaboratedReportId(1)
                .createTime(DateUtil.toDate("1499-09-09 12:12:12"))
                .description("weiqi")
                .deviceOs(1)
                .deviceType("iPhone 16 Pro Max")
                .screenshot("sdfsasdf")
                .recoveryStep("ssfsdfs")
                .scoreFromAuthor(1)
                .workerId(1)
                .taskId(1)
                .build();
        Assertions.assertEquals(1, coReportMapper.insert(coReport));
        Assertions.assertEquals(1,coReportMapper.updateByPrimaryKey(newCoReport));
    }
    @Test
    @Rollback
    public void selectByOriginReportTest() throws ParseException {
        List<CoReport> coReports=new ArrayList<>();
        for(int i=0;i<10;i++){
            CoReport coReport=CoReport.builder()
                    .id(100+i)
                    .beCollaboratedReportId(100)
                    .createTime(DateUtil.toDate("1999-09-09 12:12:12"))
                    .description("zhang"+1*32)
                    .deviceOs(1)
                    .deviceType("iPhone 14 Pro Max"+i*12)
                    .screenshot("sf")
                    .scoreFromAuthor(1)
                    .recoveryStep("ss")
                    .workerId(1)
                    .taskId(1)
                    .build();
            Assertions.assertEquals(1, coReportMapper.insert(coReport));
            coReports.add(coReport);
        }
        Assertions.assertEquals(coReports,coReportMapper.selectByOriginReport(100));
    }

    @Test
    @Rollback
    public void selectByWorkerAndOriginalReportTest() throws ParseException {
        CoReport coReport=CoReport.builder()
                .id(10)
                .beCollaboratedReportId(11)
                .createTime(DateUtil.toDate("1999-09-09 12:12:12"))
                .description("zhang")
                .deviceOs(1)
                .deviceType("iPhone 14 Pro Max")
                .screenshot("sf")
                .recoveryStep("ss")
                .scoreFromAuthor(1)
                .workerId(11)
                .taskId(1)
                .build();
        Assertions.assertEquals(1, coReportMapper.insert(coReport));
        Assertions.assertEquals(coReport,coReportMapper.selectByWorkerAndOriginalReport(11,11));
    }

    @Test
    @Rollback
    public void selectAllTest() throws ParseException {
        coReportMapper.deleteAll();
        List<CoReport> coReports=new ArrayList<>();
        for(int i=0;i<10;i++){
            CoReport coReport=CoReport.builder()
                    .id(100+i)
                    .beCollaboratedReportId(100+i)
                    .createTime(DateUtil.toDate("1999-09-09 12:12:12"))
                    .description("zhang"+1*32)
                    .deviceOs(1)
                    .deviceType("iPhone 14 Pro Max"+i*12)
                    .screenshot("sf")
                    .scoreFromAuthor(1)
                    .recoveryStep("ss")
                    .workerId(1+i)
                    .taskId(1+i)
                    .build();
            Assertions.assertEquals(1, coReportMapper.insert(coReport));
            coReports.add(coReport);
        }
        Assertions.assertEquals(coReports,coReportMapper.selectAll());
    }
    @Test
    @Rollback
    public void selectByCoWorkerIdTest() throws ParseException {
        List<CoReport> coReports=new ArrayList<>();
        for(int i=0;i<10;i++){
            CoReport coReport=CoReport.builder()
                    .id(100+i)
                    .beCollaboratedReportId(100)
                    .createTime(DateUtil.toDate("1999-09-09 12:12:12"))
                    .description("zhang"+1*32)
                    .deviceOs(1)
                    .deviceType("iPhone 14 Pro Max"+i*12)
                    .screenshot("sf")
                    .scoreFromAuthor(1)
                    .recoveryStep("ss")
                    .workerId(1)
                    .taskId(1)
                    .scoreFromAuthor(11)
                    .build();
            Assertions.assertEquals(1, coReportMapper.insert(coReport));
            coReports.add(coReport);
        }
    }

}
