package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.mapperservice.BugTableMapper;
import com.example.backendcollect.mapperservice.CriticismMapper;
import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.mapperservice.ReportMapper;
import com.example.backendcollect.po.BugTable;
import com.example.backendcollect.service.ReportBugService;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.vo.bug.BugTableVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ExtendWith(MockitoExtension.class)
public class ReportBugServiceImplTest {
    @InjectMocks
    ReportBugService reportBugService=new ReportBugServiceImpl();
    @Mock
    ReportMapper reportMapper;
    @Mock
    BugTableMapper bugTableMapper;

    @Test
    void submitNewBugTest(){}
    @Test
    void selectOldBugTest(){}
    @Test
    void setNotBugTest(){}
    @Test
    void getBugListTest(){}

    @Test
    void getBugNameByIdTest(){
        MockitoAnnotations.openMocks(this);
        BugTable bugTable=BugTable.builder().bugName("zhangwei").id(1).taskId(1).build();
        Mockito.when(bugTableMapper.selectByPrimaryKey(1)).thenReturn(bugTable);
        Assertions.assertEquals(new BugTableVO(bugTable),reportBugService.getBugNameById(1));

    }
}
