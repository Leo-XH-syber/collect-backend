package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.mq.MQSender;
import com.example.backendcollect.service.TaskService;
import com.example.backendcollect.service.UserService;
import com.example.backendcollect.serviceimpl.recommend.ALSRec;
import com.example.backendcollect.serviceimpl.recommend.RecStrategyFactory;
import com.example.backendcollect.serviceimpl.recommend.RecommendStrategy;
import com.example.backendcollect.utils.ResultHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @InjectMocks
    private TaskService taskService = new TaskServiceImpl();
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private RecommendStrategy recommendStrategy;
    @Mock
    private ResultHelper resultHelper;
    @Mock
    private MQSender mqSender;
    @Mock
    private RecStrategyFactory recStrategyFactory;

    @Test
    void createTask() {
    }

    @Test
    void getTasksByEmployer() {
    }

    @Test
    void getTasksByWorker() {
    }

    @Test
    void getAllValidTask() {
    }

    @Test
    void getTaskHall() {
    }

    @Test
    void getAllTask() {
    }

    @Test
    void getATask() {
    }

    @Test
    void getRecommendedTask() {
    }

    @Test
    void setRecommendStrategy() {
        MockitoAnnotations.openMocks(this);
        RecommendStrategy als=new ALSRec();
        Mockito.when(recStrategyFactory.getRecStrategy(any())).thenReturn(als);
        RecommendStrategy expect= recStrategyFactory.getRecStrategy(RecStrategyType.ALS);
        taskService.setRecommendStrategy(RecStrategyType.ALS);
        assertEquals(expect.strategyType,taskService.getRecommendStrategy());
    }
    @Test
    void getRecommendStrategy(){
        MockitoAnnotations.openMocks(this);
        RecommendStrategy als=new ALSRec();
        Mockito.when(recStrategyFactory.getRecStrategy(any())).thenReturn(als);
        RecommendStrategy expect= recStrategyFactory.getRecStrategy(RecStrategyType.ALS);
        taskService.setRecommendStrategy(RecStrategyType.ALS);
        assertEquals(expect.strategyType,taskService.getRecommendStrategy());
    }
}