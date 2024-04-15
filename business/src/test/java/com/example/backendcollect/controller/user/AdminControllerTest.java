package com.example.backendcollect.controller.user;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.controller.ControllerTestUtil;
import com.example.backendcollect.controller.report.CoReportController;
import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.service.CoReportService;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.service.TaskService;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.report.CoReportVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AdminControllerTest {
    @InjectMocks
    AdminController adminController;

    @Mock
    TaskService taskService;
    ResultHelper resultHelper=new ResultHelper();

    @Test
    void setRecommendStrategy() throws Exception {
        MockitoAnnotations.openMocks(this);
        adminController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(adminController).build();
        RecStrategyType recStrategyType=RecStrategyType.ALS;
        Mockito.when(taskService.setRecommendStrategy(any())).thenReturn(recStrategyType);
        String path="/admin"+"/setRecommendationType";
        String pathVal="";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.put("type", Collections.singletonList("ALS"));
        String expectJson="{\"code\":0,\"data\":\"ALS\",\"msg\":\"一切 OK\"}";
//        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post(path,pathVal)
//                .params(paraMap)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        result.getResponse().getContentAsString();
        Assertions.assertEquals(expectJson,ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,null));
    }

    @Test
    void getRecommendStrategy() {
        MockitoAnnotations.openMocks(this);
        adminController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(adminController).build();
        RecStrategyType recStrategyType=RecStrategyType.ALS;
        Mockito.when(taskService.getRecommendStrategy()).thenReturn(recStrategyType);
        String path="/admin"+"/getRecommendationType";
        String pathVal="";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"code\":0,\"data\":\"ALS\",\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson,ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }
}
