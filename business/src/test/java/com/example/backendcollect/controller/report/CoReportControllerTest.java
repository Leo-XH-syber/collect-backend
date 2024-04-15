package com.example.backendcollect.controller.report;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.controller.ControllerTestUtil;
import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.service.CoReportService;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.utils.DateUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.report.CoReportVO;
import com.example.backendcollect.vo.report.ReportVO;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CoReportControllerTest {
    @InjectMocks
    CoReportController coReportController;
    @Mock
    CoReportService coReportService;
    @Mock
    FileService fileService;
    ResultHelper resultHelper=new ResultHelper();
    JwtInfo jwtInfo =new JwtInfo();

    @Test
    public void submitCoReportTest(){
        //与提交普通报告几乎完全一致，不再详细测试
    }
    @Test
    public void getACoReport(){
        MockitoAnnotations.openMocks(this);
        coReportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(coReportController).build();
        CoReportVO coReportVO=this.getTestCoReportVO();
        Mockito.when(coReportService.getAReport(1)).thenReturn(coReportVO);
        String path="/collaReport"+"/getAReport/{collaReportId}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }
    @Test
    public void getCoReportsOfReport(){
        MockitoAnnotations.openMocks(this);
        coReportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(coReportController).build();
        List<CoReportVO> list=this.getTestCoReportVOList();
        Mockito.when(coReportService.getCoReportsOfReport(1)).thenReturn(list);
        String path="/collaReport"+"/getList/{beCollaboretedReportId}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="[{\"id\":1,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss0\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null},{\"id\":2,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss1\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null},{\"id\":3,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss2\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null},{\"id\":4,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss3\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null},{\"id\":5,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss4\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null}]";
        Assertions.assertEquals(expectJson,ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }
    @Test
    public void getCoReportByUserAndOriginalReport(){
        MockitoAnnotations.openMocks(this);
        coReportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(coReportController).build();
        CoReportVO coReportVO=this.getTestCoReportVO();
        Mockito.when(coReportService.getCoReportByUserAndOriginalReport(1,1)).thenReturn(coReportVO);
        String path="/collaReport"+"/getCoReportByUserAndOriginalReport/{workerId}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.put("originalReportId", Collections.singletonList("1"));
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson,ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }
    @Test
    void coReportCriticismByAuthorTest(){
        MockitoAnnotations.openMocks(this);
        coReportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(coReportController).build();
        CoReportVO coReportVO=this.getTestCoReportVO();
        Mockito.when(coReportService.criticismByAuthor(1,1)).thenReturn(coReportVO);
        String path="/collaReport"+"/coReportCriticism/{coReportId}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.put("score",Collections.singletonList("1"));
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":2,\"beCollaboratedReportId\":1,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"scoreFromAuthor\":null,\"difficulty\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson,ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,null));
    }


    private CoReportVO getTestCoReportVO() {
        CoReportVO report=new CoReportVO();
        report.setId(1);
        report.setTaskId(1);
        report.setWorkerId(2);
        report.setBeCollaboratedReportId(1);
//        try{
//            report.setCreateTime(DateUtil.toDate("1222-12-12 12:12:12"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        report.setDescription("sss");
        report.setDeviceOs(OSKind.HarmonyOS);
        report.setDeviceType("aa");
        report.setRecoveryStep("aa");
        report.setScreenshot("aaa");
        return report;
    }
    private List<CoReportVO> getTestCoReportVOList(){
        List<CoReportVO> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            CoReportVO report=new CoReportVO();
            report.setBeCollaboratedReportId(1);
            report.setId(1+i);
            report.setTaskId(1);
            report.setWorkerId(2);
//            try{
//                report.setCreateTime(DateUtil.toDate("1222-12-12 12:12:12"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            report.setDescription("sss"+i);
            report.setDeviceOs(OSKind.HarmonyOS);
            report.setDeviceType("aa");
            report.setRecoveryStep("aa");
            report.setScreenshot("aaa");
            list.add(report);
        }
        return list;
    }
}
