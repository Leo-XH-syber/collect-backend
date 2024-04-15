package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.UserStatusCode;
import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.mapperservice.UserMapper;
import com.example.backendcollect.mapperservice.WorkerPropertyMapper;
import com.example.backendcollect.po.User;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.service.UserService;
import com.example.backendcollect.serviceimpl.utils.WorkerStatsCalculator;
import com.example.backendcollect.utils.EncodeUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.user.UserRankVO;
import com.example.backendcollect.vo.user.UserVO;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

// 不写 @Service 不会被自动装配的
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    WorkerPropertyMapper workerPropertyMapper;

    @Autowired
    WorkerStatsCalculator workerStatsCalculator;
    private JwtInfo jwtInfo;

    @Autowired
    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }

    private ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }

    private EncodeUtil encodeUtil;

    @Autowired
    public void setEncodeUtil(EncodeUtil encodeUtil) {
        this.encodeUtil = encodeUtil;
    }


    @Override
    public UserVO userRegister(UserVO userVO) {
        //只向 user_info 表添加数据
        String email = userVO.getEmail();
        String password = userVO.getPassword();
        if (userMapper.selectByEmail(email) == null) {
            userVO.setPassword(encodeUtil.encode(password));
            User user = new User(userVO);
            userMapper.insert(user);
            user = userMapper.selectByEmail(email);
            if (user != null) {
                return new UserVO(user);
            }
        } else {
            throw new ServiceException(UserStatusCode.EMAIL_HAS_BEEN_USED);
        }
        throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
    }

    @Override
    public UserVO userLogin(String email, String password) {
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            throw new ServiceException(UserStatusCode.EMAIL_NOT_EXIST);
        } else if (!encodeUtil.matches(password, user.getPassword())) {
            throw new ServiceException(UserStatusCode.INCORRECT_USERNAME_OR_PASSWORD);
        }
        //返回一个 token
        jwtInfo.setJwtInfo(JwtInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .uname(user.getUname())
                .userRole(UserRole.values()[user.getUserRole()])
                .build());
        return new UserVO(user);
    }

    @Override
    public UserVO getUser(Integer uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            return new UserVO();
        } else {
            return new UserVO(user);
        }
    }

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public UserVO userLogout(Integer uid) {
        UserVO userVO = UserVO.builder()
                .userRole(jwtInfo.getUserRole())
                .email(jwtInfo.getEmail())
                .id(jwtInfo.getId())
                .uname(jwtInfo.getUname())
                .build();
        jwtInfo.setNull();
        return userVO;
    }

    @Override
    public WorkerPropertyVO changeUserProfile(WorkerPropertyVO workerPreferVO) {

        WorkerProperty workerPrefer = new WorkerProperty(workerPreferVO);
        if (workerPropertyMapper.updateByPrimaryKey(workerPrefer) > 0) {
            return this.getWorkerProperty(workerPrefer.getWorkerId());
        }
        throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
    }

    @Override
    public WorkerPropertyVO setUserProfile(WorkerPropertyVO workerPreferVO) {
        WorkerProperty workerPrefer = new WorkerProperty(workerPreferVO);
        if (workerPropertyMapper.insert(workerPrefer) > 0) {
            return workerPreferVO;
        }
        throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
    }

    @Override
    public WorkerPropertyVO getWorkerProperty(Integer workerId) {
        WorkerProperty workerPrefer = workerPropertyMapper.selectByPrimaryKey(workerId);
        if (workerPrefer != null) {
            return workerStatsCalculator.getWorkerAllProperty(workerPrefer);
        }
        return null;
    }

    @Override
    public WorkerPropertyVO increaseActivity(Integer workerId, Double activity) {
        User user = userMapper.selectByPrimaryKey(workerId);
        if (user.getUserRole().equals(UserRole.WORKER.getCode())) {
            workerPropertyMapper.updateActivityByWorkerIdInt(workerId, (activity.intValue()));
            return this.getWorkerProperty(workerId);
        }
        return new WorkerPropertyVO();
    }

    @Override
    public boolean increaseTaskCount(Integer workerId) {
        if (workerPropertyMapper.updateTaskCountByWorkerId(workerId) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean increaseBugReportCount(Integer workerId) {
        return workerPropertyMapper.updateBugReportCountByWorkerId(workerId) > 0;
    }

    @Override
    public List<UserRankVO> getRankList() {
        List<UserRankVO> ans = new ArrayList<>();
        List<WorkerProperty> workerList = workerPropertyMapper.selectAll();

        for (WorkerProperty worker : workerList) {
            double score = workerStatsCalculator.calFinalScore(worker);
            User workerUser = userMapper.selectByPrimaryKey(worker.getWorkerId());
            ans.add(new UserRankVO(worker.getWorkerId(), workerUser.getUname(), score));
        }
        ans.sort((a, b) -> b.getFinal_score().compareTo(a.getFinal_score()));
        return ans.size() > 20 ? ans.subList(0, 20) : ans;
    }
}
