package com.example.backendcollect.controller.report;

import com.example.backendcollect.controller.ControllerTestUtil;
import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.mapperservice.BugTableMapper;
import com.example.backendcollect.po.BugTable;
import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.service.ReportBugService;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.utils.DateUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.ResultVO;
import com.example.backendcollect.vo.bug.BugTableVO;
import com.example.backendcollect.vo.file.FileInfoVO;
import com.example.backendcollect.vo.report.CriticismVO;
import com.example.backendcollect.vo.report.ReportClusterVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

import java.io.File;
import java.text.ParseException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class ReportControllerTest {
    @InjectMocks
    ReportController reportController;
    @Mock
    ReportService reportService;
    @Mock
    FileService fileService;
    @Mock
    ReportBugService reportBugService;
    @Mock
    BugTableMapper bugTableMapper;

    ResultHelper resultHelper=new ResultHelper();
    @Test
    public void getAReportTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        ReportVO reportVO=this.getTestReportVO();
        Mockito.when(reportService.getAReport(1)).thenReturn(reportVO);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":11.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(ControllerTestUtil.get(mockMvc,"/report/getAReport/{reportId}","1",new LinkedMultiValueMap<>()),expectJson);
    }
    @Test
    public void getReportsOfTaskTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        List<ReportVO> reportVOS=this.getTestReportVOList();
        Mockito.when(reportService.getAllReportOfTask(1)).thenReturn(reportVOS);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String expectJson="[{\"id\":1,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss0\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":0.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":2,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss1\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":11.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":3,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss2\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":22.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":4,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss3\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":33.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":5,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss4\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":44.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null}]";
        MvcResult result;
        try {
            result=mockMvc.perform(MockMvcRequestBuilders.get("/report/taskAllReports/{taskid}",1).accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assertions.assertEquals(result.getResponse().getContentAsString(),expectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getUserTaskReportTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        ReportVO reportVO=this.getTestReportVO();
        Mockito.when(reportService.getReportByWorkerAndTask(1,1)).thenReturn(reportVO);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":11.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},\"msg\":\"ä¸\u0080å\u0088\u0087 OK\"}";
        MvcResult result;
        try {
            result=mockMvc.perform(MockMvcRequestBuilders.get("/report/userTaskReport/{uid}",1)
                    .param("taskId","1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assertions.assertEquals(result.getResponse().getContentAsString(),expectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void criticizeReportTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        CriticismVO criticismVO=this.getTestCriticismVO();
        Mockito.when(reportService.criticizeReport(any(CriticismVO.class))).thenReturn(criticismVO);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"reportId\":1,\"workerId\":2,\"createTime\":null,\"score\":11,\"comments\":\"p16sdf\"},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(ControllerTestUtil.post(mockMvc,"/report/criticism","",new LinkedMultiValueMap<>(),criticismVO),expectJson);
    }
    @Test
    public void getSortedReportsOfTaskTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/sortedAll/{taskId}";
        String pathVal="1";

        List<ReportVO> list=this.getTestReportVOList();
        PageInfo<ReportVO> page=new PageInfo<>(list);
        Mockito.when(reportService.getReportsOfTaskByScore(1,false,1)).thenReturn(page);

        String expectJson="{\"total\":5,\"list\":[{\"id\":1,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss0\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":0.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":2,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss1\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":11.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":3,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss2\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":22.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":4,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss3\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":33.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":5,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss4\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":44.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null}],\"pageNum\":1,\"pageSize\":5,\"size\":5,\"startRow\":0,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}";
        try {
            MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get(path,pathVal)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .param("sortType","Score")
                    .param("reverse","false")
                    .param("page","1")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assertions.assertEquals(result.getResponse().getContentAsString(),expectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.reverse(list);
        page=new PageInfo<>(list);
        Mockito.when(reportService.getReportsOfTaskByScore(1,true,1)).thenReturn(page);

        expectJson="{\"total\":5,\"list\":[{\"id\":5,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss4\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":44.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":4,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss3\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":33.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":3,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss2\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":22.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":2,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss1\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":11.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},{\"id\":1,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss0\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":0.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null}],\"pageNum\":1,\"pageSize\":5,\"size\":5,\"startRow\":0,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}";
        try {
            MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get(path,pathVal)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .param("sortType","Score")
                    .param("reverse","false")
                    .param("page","1")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assertions.assertEquals(result.getResponse().getContentAsString(),expectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void getUserCriticismOfReportTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/getCriticism/{uid}";
        String pathVal="1";

        CriticismVO criticismVO=this.getTestCriticismVO();
        Mockito.when(reportService.getUserCriticismOfReport(1,1)).thenReturn(criticismVO);
        MultiValueMap<String,String> paramMap=new LinkedMultiValueMap<>();
        paramMap.put("reportId", Collections.singletonList("1"));
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"reportId\":1,\"workerId\":2,\"createTime\":null,\"score\":11,\"comments\":\"p16sdf\"},\"msg\":\"一切 OK\"}";
        try {
            MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get(path,pathVal)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .params(paramMap)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assertions.assertEquals(result.getResponse().getContentAsString(),expectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getSimilarListTest(){

    }
    @Test
    public void getSimilarityOfTwoTest(){

    }
    @Test
    public void getAllScoresOfAReportTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/getAllScores/{reportId}";
        String pathVal="1";

        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(44);
        Mockito.when(reportService.getAllScoresOfAReport(1)).thenReturn(list);

        String expectJson="[1,2,44]";
        MultiValueMap<String,String> paramMap=new LinkedMultiValueMap<>();
        try {
            MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get(path,pathVal)
                    .accept(MediaType.APPLICATION_JSON_UTF8).params(paramMap))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assertions.assertEquals(result.getResponse().getContentAsString(),expectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getAllCriticismsOfAReportTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/getAllCriticisms/{reportId}";
        String pathVal="1";

        List<CriticismVO> list=this.getTestCriticismVOList();
        Mockito.when(reportService.getAllCriticismsOfAReport(1)).thenReturn(list);

        String expectJson="[{\"id\":1,\"reportId\":1,\"workerId\":2,\"createTime\":null,\"score\":11,\"comments\":\"p16sdf\"},{\"id\":2,\"reportId\":1,\"workerId\":2,\"createTime\":null,\"score\":11,\"comments\":\"p16sdf\"},{\"id\":3,\"reportId\":1,\"workerId\":2,\"createTime\":null,\"score\":11,\"comments\":\"p16sdf\"}]";
        try {
            MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get(path,pathVal)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assertions.assertEquals(result.getResponse().getContentAsString(),expectJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void submitReportTest() throws Exception {
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/submit";
        ReportVO reportVO=this.getTestReportVO();
        Mockito.when(reportService.submitReport(any())).thenReturn(reportVO);
        FileInfoVO fileInfoVO=new FileInfoVO("1","1", (long) 1);
        Mockito.when(fileService.uploadFile(any())).thenReturn(fileInfoVO);

        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":2,\"taskId\":1,\"createTime\":null,\"deviceOs\":\"HarmonyOS\",\"deviceType\":\"aa\",\"description\":\"sss\",\"recoveryStep\":\"aa\",\"screenshot\":\"aaa\",\"avg_score\":11.0,\"bugReport\":null,\"bugId\":null,\"difficulty\":null},\"msg\":\"ä¸\u0080å\u0088\u0087 OK\"}";

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("file", "other-file-name.data", "text/plain", "some other type".getBytes());

        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.fileUpload(path)
                .file(firstFile)
                .file(secondFile)
                .param("id", reportVO.getId()+"")
                .param("workerId",reportVO.getWorkerId()+"")
                .param("taskId",reportVO.getTaskId()+"")

                .param("deviceOs","HarmonyOS")
                .param("deviceType",reportVO.getDeviceType())
                .param("description",reportVO.getDescription())
                .param("recoveryStep",reportVO.getRecoveryStep())
                .param("screenShot",reportVO.getScreenshot())
                .param("avg_score",reportVO.getAvg_score()+""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals(expectJson,result.getResponse().getContentAsString());
    }
    @Test
    void checkReportTest() throws Exception{
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/employerExamReport/{reportId}";
        String pathVal="1";

        BugTableVO bugTableVO= BugTableVO.builder()
                .id(1)
                .bugName("1")
                .taskId(1)
                .build();
        Mockito.when(reportBugService.submitNewBug(1,1,"1")).thenReturn(bugTableVO);
        Mockito.when(reportBugService.selectOldBug(1,1,"1")).thenReturn(bugTableVO);
//        Mockito.when(reportBugService.setNotBug(1))

        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.put("reportId",Collections.singletonList("1"));
        paraMap.put("taskId",Collections.singletonList("1"));
        paraMap.put("reportBugType",Collections.singletonList("newBug"));
        paraMap.put("bug",Collections.singletonList("1"));

        String expectJson="{\"code\":0,\"data\":\"1\",\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,null));
    }
    @Test
    void getReportClustersTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/reportClusters/{taskId}";
        String pathVal="1";
        List<ReportClusterVO> list=new ArrayList<>();
        list.add(ReportClusterVO.builder().clusterIndex(1).reportList(null).wordInfo(null).build());
        Mockito.when(reportService.getReportClusters(1)).thenReturn(list);
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();

        String expectJson="[{\"clusterIndex\":1,\"reportList\":null,\"wordInfo\":null}]";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }
    @Test
    void getBugNameByIdTest(){
        MockitoAnnotations.openMocks(this);
        reportController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(reportController).build();
        String path="/report"+"/getBugNameById/{bugId}";
        String pathVal="1";
        BugTableVO bugTable=BugTableVO.builder().bugName("zhangwei").id(1).taskId(1).build();
        Mockito.when(reportBugService.getBugNameById(1)).thenReturn(bugTable);
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"taskId\":1,\"bugName\":\"zhangwei\"},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }



    private ReportVO getTestReportVO() {
        ReportVO report=new ReportVO();
        report.setAvg_score(11.0);
        report.setId(1);
        report.setTaskId(1);
        report.setWorkerId(2);
//        try{
//            report.setCreateTime(DateUtil.toDate("2021-12-12 12:12:12"));
////            long time=8000;
////            report.setCreateTime(new Date((long)-23574138468000));
//
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
    private List<ReportVO> getTestReportVOList(){
        List<ReportVO> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            ReportVO report=new ReportVO();
            report.setAvg_score(11.0*i);
            report.setId(1+i);
            report.setTaskId(1);
            report.setWorkerId(2);
//            try{
//                report.setCreateTime(DateUtil.toDate("2021-12-12 12:12:12"));
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
    private CriticismVO getTestCriticismVO(){
        CriticismVO criticism=new CriticismVO();
        criticism.setId(1);
        criticism.setComments("p16sdf");
//        try {
//            criticism.setCreateTime(DateUtil.toDate("2021-12-12 12:12:12"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        criticism.setReportId(1);
        criticism.setScore(11);
        criticism.setWorkerId(2);
        return criticism;
    }
    private List<CriticismVO> getTestCriticismVOList(){
        List<CriticismVO>list=new ArrayList<>();
        for(int i=0;i<3;i++){
            CriticismVO criticism=new CriticismVO();
            criticism.setId(1+i);
            criticism.setComments("p16sdf");
//            try {
//                criticism.setCreateTime(DateUtil.toDate("2021-12-12 12:12:12"));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            criticism.setReportId(1);
            criticism.setScore(11);
            criticism.setWorkerId(2);
            list.add(criticism);
        }

        return list;
    }
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
