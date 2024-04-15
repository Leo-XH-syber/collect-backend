package com.example.backendcollect.controller.user;

import com.example.backendcollect.controller.ControllerTestUtil;
import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.service.OrderService;
import com.example.backendcollect.service.UserService;
//import com.example.backendcollect.utils.DateUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.Order.OrderVO;
import com.example.backendcollect.vo.user.UserAllInfoVO;
import com.example.backendcollect.vo.user.UserFormVO;
import com.example.backendcollect.vo.user.UserVO;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.util.Collections;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class UserControllerTest {
    @InjectMocks
    UserController userController;
    @Mock
    UserService userService;
    @Mock
    OrderService orderService;
    ResultHelper resultHelper=new ResultHelper();
    @Test
    void login() {
        MockitoAnnotations.openMocks(this);
        userController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(userController).build();
        UserVO userVO=this.getUserVO();
        UserFormVO userForm=this.getUserFormVO();
        Mockito.when(userService.userLogin(userForm.getEmail(), userForm.getPassword())).thenReturn(userVO);
        String path="/user"+"/login";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"uname\":\"rikka\",\"email\":\"123456@qq.com\",\"password\":\"pcYq0hFxxpKBzPIDlxy1Yesov7EcT4aQZsXTsrkAcUyJRm1hvFJuC\",\"userRole\":\"WORKER\",\"createTime\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,userForm));
    }

    @Test
    void logout() {
        MockitoAnnotations.openMocks(this);
        userController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(userController).build();
        UserVO userVO=this.getUserVO();
        Mockito.when(userService.userLogout(1)).thenReturn(userVO);
        String path="/user"+"/logout";
        String pathVal="";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.put("uid", Collections.singletonList("1"));
        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"uname\":\"rikka\",\"email\":\"123456@qq.com\",\"password\":\"pcYq0hFxxpKBzPIDlxy1Yesov7EcT4aQZsXTsrkAcUyJRm1hvFJuC\",\"userRole\":\"WORKER\",\"createTime\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,null));
    }

    @Test
    void register() {
        MockitoAnnotations.openMocks(this);
        userController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(userController).build();
        UserVO userVO=this.getUserVO();
        userVO.setPassword("123456");
        WorkerPropertyVO workerPreferVO=this.getWorkerPreferVO();
        UserAllInfoVO userAllInfoVO=new UserAllInfoVO(userVO,workerPreferVO);
        Mockito.when(userService.userRegister(userVO)).thenReturn(userVO);
        Mockito.when(userService.setUserProfile(workerPreferVO)).thenReturn(workerPreferVO);
        String path="/user"+"/register";
        String pathVal="";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        String expectJson="{\"code\":0,\"data\":{\"uid\":1,\"uname\":\"rikka\",\"email\":\"123456@qq.com\",\"password\":null,\"userRole\":\"WORKER\",\"createTime\":null,\"workerId\":1,\"ability\":1.1,\"activity\":1,\"defaultOs\":\"iOS\",\"preference\":[\"COMPATIBILITY_TEST\",\"COMPATIBILITY_TEST\"]},\"msg\":\"一切 OK\"}";
        userAllInfoVO.setPassword("123456");
        Assertions.assertEquals(expectJson, ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,userAllInfoVO));
    }

    @Test
    void changeUserProfile() {
        MockitoAnnotations.openMocks(this);
        userController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(userController).build();
        UserVO userVO=this.getUserVO();
        WorkerPropertyVO workerPreferVO=this.getWorkerPreferVO();
        UserAllInfoVO userAllInfoVO=new UserAllInfoVO(userVO,workerPreferVO);
        Mockito.when(userService.getUser(1)).thenReturn(userVO);
        Mockito.when(userService.changeUserProfile(workerPreferVO)).thenReturn(workerPreferVO);
        String path="/user"+"/changeProfile";
        String pathVal="";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();

        String expectJson="{\"code\":0,\"data\":{\"uid\":1,\"uname\":\"rikka\",\"email\":\"123456@qq.com\",\"password\":null,\"userRole\":\"WORKER\",\"createTime\":null,\"workerId\":1,\"ability\":1.1,\"activity\":1,\"defaultOs\":\"iOS\",\"preference\":[\"COMPATIBILITY_TEST\",\"COMPATIBILITY_TEST\"]},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.post(mockMvc,path,pathVal,paraMap,userAllInfoVO));
    }

    @Test
    void getUser() {
        MockitoAnnotations.openMocks(this);
        userController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(userController).build();
        UserVO userVO=this.getUserVO();
        WorkerPropertyVO workerPreferVO=this.getWorkerPreferVO();
        UserAllInfoVO userAllInfoVO=new UserAllInfoVO(userVO,workerPreferVO);
        Mockito.when(userService.getUser(1)).thenReturn(userVO);
        Mockito.when(userService.getWorkerProperty(1)).thenReturn(workerPreferVO);
        String path="/user"+"/{uid}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();

        String expectJson="{\"code\":0,\"data\":{\"uid\":1,\"uname\":\"rikka\",\"email\":\"123456@qq.com\",\"password\":null,\"userRole\":\"WORKER\",\"createTime\":null,\"workerId\":1,\"ability\":1.1,\"activity\":1,\"defaultOs\":\"iOS\",\"preference\":[\"COMPATIBILITY_TEST\",\"COMPATIBILITY_TEST\"]},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }

    @Test
    void userTaskInfo() throws ParseException {
        MockitoAnnotations.openMocks(this);
        userController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(userController).build();
        UserVO userVO=this.getUserVO();
        OrderVO orderVO=OrderVO.builder()
                .id(1)
                .orderState(OrderState.EXPIRED)
//                .selectTime(DateUtil.toDate("2021-12-12 12:12:12"))
                .workerId(1)
                .taskId(1)
                .build();
        Mockito.when(orderService.queryOrder(1,1)).thenReturn(orderVO);

        String path="/user"+"/userTaskInfo/{uid}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();
        paraMap.put("taskId",Collections.singletonList("1"));

        String expectJson="{\"code\":0,\"data\":{\"id\":1,\"workerId\":1,\"taskId\":1,\"selectTime\":null,\"orderState\":\"EXPIRED\"},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }
    @Test
    void workerAttributeTest() throws ParseException{
        MockitoAnnotations.openMocks(this);
        userController.setResultHelper(resultHelper);
        MockMvc mockMvc=standaloneSetup(userController).build();
        WorkerPropertyVO workerPropertyVO=WorkerPropertyVO.builder()
                .workerId(1)
                .activity(1)
                .ability(1.0).build();
        Mockito.when(userService.getWorkerProperty(1)).thenReturn(workerPropertyVO);

        String path="/user"+"/workerAttribute/{uid}";
        String pathVal="1";
        MultiValueMap<String ,String> paraMap=new LinkedMultiValueMap<>();

        String expectJson="{\"code\":0,\"data\":{\"workerId\":1,\"ability\":1.0,\"activity\":1,\"defaultOs\":null,\"preference\":null,\"cooperationAbility\":null,\"criticismAbility\":null,\"reportAvgCriticism\":null,\"avgRepeatability\":null,\"taskCount\":null,\"historyBugCount\":null,\"bugReportProportion\":null,\"duplicateIndex\":null,\"syntheticalScore\":null},\"msg\":\"一切 OK\"}";
        Assertions.assertEquals(expectJson, ControllerTestUtil.get(mockMvc,path,pathVal,paraMap));
    }


    private UserVO getUserVO(){
        UserVO user = new UserVO();
        user.setUserRole(UserRole.WORKER);
        user.setEmail("123456@qq.com");
        user.setId(1);
        user.setPassword("pcYq0hFxxpKBzPIDlxy1Yesov7EcT4aQZsXTsrkAcUyJRm1hvFJuC");
        user.setUname("rikka");
//        try {
////            user.setCreateTime(DateUtil.toDate("2021-12-12 12:12:12"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return user;
    }
    private UserFormVO getUserFormVO(){
        UserFormVO userFormVO=new UserFormVO();
        userFormVO.setEmail("1@1.1");
        userFormVO.setPassword("123456");
        return userFormVO;
    }
    private WorkerPropertyVO getWorkerPreferVO(){
        WorkerPropertyVO workerPreferVO=new WorkerPropertyVO();
        workerPreferVO.setAbility(1.1);
        workerPreferVO.setPreference(new TaskType[]{TaskType.COMPATIBILITY_TEST, TaskType.COMPATIBILITY_TEST});
        workerPreferVO.setDefaultOs(OSKind.iOS);
        workerPreferVO.setWorkerId(1);
        workerPreferVO.setActivity(1);
        return workerPreferVO;
    }

}
