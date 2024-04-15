package com.example.backendcollect.controller.task;

import com.example.backendcollect.controller.ControllerTestUtil;
import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.enums.types.TaskState;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.service.OrderService;
import com.example.backendcollect.service.ReportBugService;
import com.example.backendcollect.service.TaskService;
import com.example.backendcollect.utils.DateUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.Order.OrderVO;
import com.example.backendcollect.vo.bug.BugTableVO;
import com.example.backendcollect.vo.file.FileInfoVO;
import com.example.backendcollect.vo.report.CoReportVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.example.backendcollect.vo.task.TaskVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class TaskControllerTest {
    @InjectMocks
    TaskController taskController;
    @Mock
    TaskService taskService;
    @Mock
    OrderService orderService;
    @Mock
    FileService fileService;
    @Mock
    ReportBugService reportBugService;
    ResultHelper resultHelper=new ResultHelper();


    @Test
    void launchTask() throws Exception {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        String path="/task"+"/submit";
        String pathVal="";
        TaskVO taskVO=this.getTaskVO();
//        taskVO.setStartTime(DateUtil.toDate("2021-12-12 12:12:12"));
//        taskVO.setEndTime(DateUtil.toDate("2021-12-12 12:12:12"));
        taskVO.setEmployerId(1);
        taskVO.setHasWorkers(0);
        taskVO.setTaskState(TaskState.IN_PROCESS);
        FileInfoVO fileInfoVO=new FileInfoVO("1","1", (long) 1);
        Mockito.when(fileService.uploadFile(any())).thenReturn(fileInfoVO);
        Mockito.when(taskService.createTask(taskVO)).thenReturn(taskVO);
        Mockito.when(taskService.createTask(any())).thenReturn(taskVO);
        MultiValueMap<String,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"code\":0,\"data\":{\"id\":null,\"employerId\":1,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},\"msg\":\"ä¸\u0080å\u0088\u0087 OK\"}";

        MockMultipartFile firstFile = new MockMultipartFile("execFile", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("requirementFile", "other-file-name.data", "text/plain", "some other type".getBytes());
//        paraMap.set("startTime","2021-12-12 12:12:12");
//        paraMap.set("endTime","2021-12-12 12:12:12");
        paraMap.set("employerId","1");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.fileUpload(path)
                .file(firstFile)
                .file(secondFile)
                .params(paraMap))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals(expectJson,result.getResponse().getContentAsString());
    }

    @Test
    void getEmployerTask() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        List<TaskVO>list=this.getTaskVOList();
        PageInfo<TaskVO> pageInfo=new PageInfo<>(list);
        Mockito.when(taskService.getTasksByEmployer(1,1,1)).thenReturn(pageInfo);
        String path="/task"+"/getEmployerTask/{page}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.set("uid","1");
        paraMap.set("type","PERSON_FULL");
        String expectJson="{\"total\":5,\"list\":[{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null}],\"pageNum\":1,\"pageSize\":5,\"size\":5,\"startRow\":0,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    @Test
    void getAllValidTask() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        List<TaskVO>list=this.getTaskVOList();
        PageInfo<TaskVO> pageInfo=new PageInfo<>(list);
        Mockito.when(taskService.getAllValidTask(1,1)).thenReturn(pageInfo);
        String path="/task"+"/getAllValid/{page}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.set("uid","1");
        String expectJson="{\"total\":5,\"list\":[{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null}],\"pageNum\":1,\"pageSize\":5,\"size\":5,\"startRow\":0,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    @Test
    void getTaskHall() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        List<TaskVO>list=this.getTaskVOList();
        PageInfo<TaskVO> pageInfo=new PageInfo<>(list);
        Mockito.when(taskService.getTaskHall(1)).thenReturn(pageInfo);
        String path="/task"+"/getTaskHall/{page}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"total\":5,\"list\":[{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null}],\"pageNum\":1,\"pageSize\":5,\"size\":5,\"startRow\":0,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    @Test
    void getPagedWorkerTasks() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        List<TaskVO>list=this.getTaskVOList();
        PageInfo<TaskVO> pageInfo=new PageInfo<>(list);
        Mockito.when(taskService.getTasksByWorker(1,1,0)).thenReturn(pageInfo);
        String path="/task"+"/getPagedWorkerTasks/{page}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.set("uid","1");
        paraMap.set("type","IN_PROCESS");
        String expectJson="{\"total\":5,\"list\":[{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null}],\"pageNum\":1,\"pageSize\":5,\"size\":5,\"startRow\":0,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    @Test
    void getAllTask() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        List<TaskVO>list=this.getTaskVOList();
        PageInfo<TaskVO> pageInfo=new PageInfo<>(list);
        Mockito.when(taskService.getAllTask(1)).thenReturn(pageInfo);
        String path="/task"+"/getAll/{page}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"total\":5,\"list\":[{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null}],\"pageNum\":1,\"pageSize\":5,\"size\":5,\"startRow\":0,\"endRow\":4,\"pages\":1,\"prePage\":0,\"nextPage\":0,\"isFirstPage\":true,\"isLastPage\":true,\"hasPreviousPage\":false,\"hasNextPage\":false,\"navigatePages\":8,\"navigatepageNums\":[1],\"navigateFirstPage\":1,\"navigateLastPage\":1}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    @Test
    void selectTask() throws ParseException {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        OrderVO orderVO=OrderVO.builder()
                .id(1)
                .orderState(OrderState.EXPIRED)
//                .selectTime(DateUtil.toDate("2021-12-12 12:12:12"))
                .workerId(1)
                .taskId(1)
                .build();
        Mockito.when(orderService.selectTask(1,1)).thenReturn(orderVO);
        String path="/task"+"/select/{taskid}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.set("uid","1");
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":1,\"taskId\":1,\"selectTime\":null,\"orderState\":\"EXPIRED\"},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,null));
    }

    @Test
    void getTaskInfo() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        TaskVO taskVO=this.getTaskVO();
        Mockito.when(taskService.getATask(1)).thenReturn(taskVO);
        String path="/task"+"/getTaskInfo/{taskId}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();

        String expectJson="{\"code\":0,\"data\":{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    @Test
    void getRecommended() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        List<TaskVO>list=this.getTaskVOList();
        Mockito.when(taskService.getRecommendedTask(1)).thenReturn(list);
        String path="/task"+"/getRecommended/{uid}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="[{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null},{\"id\":null,\"employerId\":null,\"taskName\":null,\"startTime\":null,\"endTime\":null,\"taskType\":null,\"workerNum\":null,\"hasWorkers\":0,\"taskState\":\"IN_PROCESS\",\"testApp\":null,\"testDoc\":null,\"difficulty\":null,\"introduction\":null,\"os\":null}]";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }
    @Test
    void getBugList() {
        MockitoAnnotations.openMocks(this);
        taskController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(taskController).build();
        List<BugTableVO> list=new ArrayList<>();
        list.add(BugTableVO.builder().bugName("zhangwei").id(1).taskId(1).build());
        Mockito.when(reportBugService.getBugList(1)).thenReturn(list);
        String path="/task"+"/getBugList/{taskId}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="[{\"id\":1,\"taskId\":1,\"bugName\":\"zhangwei\"}]";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    private List<TaskVO> getTaskVOList(){
        List<TaskVO> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            TaskVO task=new TaskVO();

            list.add(task);
        }
        return list;
    }
    private TaskVO getTaskVO(){
        return new TaskVO();
    }
}
