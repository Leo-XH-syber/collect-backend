package com.example.backendcollect.mapperservice;


import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.utils.DateUtil;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.text.ParseException;

@SpringBootTest
//@RunWith(SpringRunner.class)
@MybatisTest //这个是启用自己配置的数据元，不加则采用虚拟数据源
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class WorkerPreferMapperTest {
    @Resource
    WorkerPropertyMapper workerPreferMapper;
    @Resource
    ReportMapper reportMapper;
    @Resource
    CriticismMapper criticismMapper;

    @Test
    @Rollback
    public void insertTest(){
        WorkerProperty workerPrefer=WorkerProperty.builder()
                .workerId(1)
                .activity(10)
                .defaultOs(1)
                .preference("1;2")
                .build();
        Assertions.assertEquals(1,workerPreferMapper.insert(workerPrefer));
    }
    @Test
    @Rollback
    public void selectByPrimaryKeyTest(){
        WorkerProperty workerPrefer=new WorkerProperty();
        workerPrefer.setWorkerId(1);
        workerPrefer.setAbility(1.0);
        workerPrefer.setActivity(10);
        workerPrefer.setDefaultOs(1);
        workerPrefer.setPreference("1;2");
        Assertions.assertEquals(1,workerPreferMapper.insert(workerPrefer));
        Assertions.assertEquals(workerPrefer,workerPreferMapper.selectByPrimaryKey(1));
    }
    @Test
    @Rollback
    public void updateByPrimaryKeyTest(){
        WorkerProperty workerPrefer=WorkerProperty.builder()
                .workerId(1)
                .activity(10)
                .defaultOs(1)
                .preference("1;2")
                .build();
        Assertions.assertEquals(1,workerPreferMapper.insert(workerPrefer));
        workerPrefer=WorkerProperty.builder()
                .workerId(1)
                .activity(100)
                .defaultOs(3)
                .preference("1;4")
                .build();
        Assertions.assertEquals(1,workerPreferMapper.updateByPrimaryKey(workerPrefer));
    }
    @Test
    @Rollback
    public void updateActivityByWorkerIdIntTest(){
        WorkerProperty workerPrefer=new WorkerProperty();
        workerPrefer.setWorkerId(1);
        workerPrefer.setAbility(1.0);
        workerPrefer.setActivity(10);
        workerPrefer.setDefaultOs(1);
        workerPrefer.setPreference("1;2");
        Assertions.assertEquals(1,workerPreferMapper.insert(workerPrefer));
        Assertions.assertEquals(1,workerPreferMapper.updateActivityByWorkerIdInt(1,20));
        workerPrefer.setActivity(30);
        Assertions.assertEquals(workerPrefer,workerPreferMapper.selectByPrimaryKey(1));
    }
    @Test
    @Rollback
    public void getWorkerAbilityTest() throws ParseException {
        WorkerProperty workerPrefer=WorkerProperty.builder()
                .workerId(11)
                .activity(10)
                .defaultOs(1)
                .preference("1;2")
                .build();
        Assertions.assertEquals(1,workerPreferMapper.insert(workerPrefer));
        Report report=Report.builder()
                .createTime(DateUtil.toDate("2022-01-01 12:12:12"))
                .description("aa")
                .deviceOs(1)
                .deviceType("aa")
                .id(11)
                .bugId(1)
                .bugReport(1)
                .recoveryStep("aa")
                .screenshot("aa")
                .taskId(1)
                .workerId(11)
                .build();
        Assertions.assertEquals(1,reportMapper.insert(report));

        for(int i=0;i<2;i++){
            Criticism criticism=Criticism.builder()
                    .id(100+i)
                    .comments("zhangwei")
                    .createTime(DateUtil.toDate("1989-10-19 12:12:12"))
                    .reportId(11)
                    .score(10)
                    .workerId(100+i)
                    .build();
            Assertions.assertEquals(1,criticismMapper.insert(criticism));
        }
        Assertions.assertEquals(10.0,workerPreferMapper.getWorkerAbility(11));
    }
}
