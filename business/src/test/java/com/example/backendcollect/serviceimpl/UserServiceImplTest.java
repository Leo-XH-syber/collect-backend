package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.mapperservice.UserMapper;
import com.example.backendcollect.mapperservice.WorkerPropertyMapper;
import com.example.backendcollect.po.User;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.service.UserService;
import com.example.backendcollect.utils.EncodeUtil;
import com.example.backendcollect.vo.user.UserVO;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;


/**
 * 单元测试不必启动整个 Spring 框架(划掉), service 层也不必真的连接数据库
 * 前半句话存疑... 我也没亲眼见过真实的单元测试长什么样, 此处我就是为了省时间(启动 spring 好几秒)
 * 现在这些简单的类应该够用了, 所以不依赖 Spring 的自动注入也能测试
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    // 待测类的对象
    @InjectMocks
    private UserService userService = new UserServiceImpl();

    // 被依赖的非静态对象, 由 Mockito 提供模拟实现, 它会清空所有的属性和方法, 需要通过 when 来指定(见下面)
    @Mock
    private UserMapper userMapper;

    @Mock
    private EncodeUtil encodeUtil;
    @Mock
    private WorkerPropertyMapper workerPreferMapper;

    @Mock
    private JwtInfo jwtInfo;
//    @Mock
//    ResultVO<UserVO> resultVO;

    @Before
    public void setup() {
    }

    @Test
    void userRegister() {
        MockitoAnnotations.openMocks(this); // 不能少
        User user = new User();
        user.setUserRole(0);
        user.setEmail("123456@qq.com");
        user.setId(0);
        user.setPassword("pcYq0hFxxpKBzPIDlxy1Yesov7EcT4aQZsXTsrkAcUyJRm1hvFJuC");
        user.setUname("rikka");

        // 预计的返回值
        UserVO expect = new UserVO(user);


        // 假设 mapper 在收到一个邮箱的时候返回的东西
        Mockito.when(userMapper.selectByEmail("123456@qq.com")).thenReturn(user);

        Mockito.when(encodeUtil.matches("123456", "pcYq0hFxxpKBzPIDlxy1Yesov7EcT4aQZsXTsrkAcUyJRm1hvFJuC")).thenReturn(true);

        Mockito.doNothing().when(jwtInfo).setJwtInfo(JwtInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .uname(user.getUname())
                .userRole(UserRole.values()[user.getUserRole()])
                .build());
        UserVO ret = userService.userLogin("123456@qq.com", "123456");

        // 此处验证的是返回在模拟 mapper 情况下能返回正确的值
        // 其实还能添加方法被调用次数的验证
        Assertions.assertEquals(ret, expect);
    }

    @Test
    void userLogin() {
    }

    @Test
    void getUser() {
    }

    @Test
    void selectByEmail() {
    }
    @Test
    void setUserProfile(){
        MockitoAnnotations.openMocks(this);
        WorkerPropertyVO workerPreferVO=new WorkerPropertyVO();
        workerPreferVO.setWorkerId(1);
        workerPreferVO.setDefaultOs(OSKind.iOS);
        workerPreferVO.setPreference(new TaskType[]{TaskType.COMPATIBILITY_TEST});
        Mockito.when(workerPreferMapper.insert(any(WorkerProperty.class))).thenReturn(1);
        Assertions.assertEquals(workerPreferVO,userService.setUserProfile(workerPreferVO));
    }
    @Test
    void changeUserProfile(){
        MockitoAnnotations.openMocks(this);
        WorkerPropertyVO workerPreferVO=new WorkerPropertyVO();
        workerPreferVO.setWorkerId(1);
        workerPreferVO.setAbility(1.1);
        workerPreferVO.setDefaultOs(OSKind.iOS);
        workerPreferVO.setPreference(new TaskType[]{TaskType.COMPATIBILITY_TEST});
        lenient().when(workerPreferMapper.updateByPrimaryKey(any(WorkerProperty.class))).thenReturn(1);
        lenient().when(workerPreferMapper.selectByPrimaryKey(1)).thenReturn(new WorkerProperty(workerPreferVO));
        lenient().when(workerPreferMapper.getWorkerAbility(1)).thenReturn(1.1);
//        Assertions.assertEquals(workerPreferVO,userService.changeUserProfile(workerPreferVO));
    }
    @Test
    void getWorkerPrefer(){
        MockitoAnnotations.openMocks(this);
        WorkerPropertyVO workerPreferVO=new WorkerPropertyVO();
        workerPreferVO.setWorkerId(1);
        workerPreferVO.setAbility(1.1);
        workerPreferVO.setDefaultOs(OSKind.iOS);
        workerPreferVO.setPreference(new TaskType[]{TaskType.COMPATIBILITY_TEST});
        lenient().when(workerPreferMapper.selectByPrimaryKey(1)).thenReturn(new WorkerProperty(workerPreferVO));
        lenient().when(workerPreferMapper.getWorkerAbility(1)).thenReturn(1.1);
//        Assertions.assertEquals(workerPreferVO,userService.getWorkerProperty(1));
    }
    @Test
    void increaseActivity(){
        MockitoAnnotations.openMocks(this);
        WorkerPropertyVO workerPreferVO=new WorkerPropertyVO();
        workerPreferVO.setWorkerId(1);
        workerPreferVO.setAbility(1.1);
        workerPreferVO.setDefaultOs(OSKind.iOS);
        workerPreferVO.setPreference(new TaskType[]{TaskType.COMPATIBILITY_TEST});
        UserVO userVO=new UserVO();
        userVO.setId(1);
        userVO.setCreateTime(new Date());
        userVO.setEmail("1@1.1");
        userVO.setUname("zhang");
        userVO.setUserRole(UserRole.WORKER);
        lenient().when(workerPreferMapper.updateActivityByWorkerIdInt(1,10)).thenReturn(1);
        lenient().when(workerPreferMapper.selectByPrimaryKey(1)).thenReturn(new WorkerProperty(workerPreferVO));
        lenient().when(userMapper.selectByPrimaryKey(1)).thenReturn(new User(userVO));
        lenient().when(workerPreferMapper.getWorkerAbility(1)).thenReturn(1.1);

//        Assertions.assertEquals(workerPreferVO,userService.increaseActivity(1,10.0));
    }
}
