package com.example.backendcollect.service;

import com.example.backendcollect.po.User;
import com.example.backendcollect.vo.user.UserRankVO;
import com.example.backendcollect.vo.user.UserVO;
import com.example.backendcollect.vo.user.WorkerPropertyVO;

import java.util.List;

public interface UserService {
    // 用户注册
    UserVO userRegister(UserVO user);

    // 用户登录验证
    UserVO userLogin(String phone, String password);

    // 根据id查找用户
    UserVO getUser(Integer uid);

    List<User> getAll();

    UserVO userLogout(Integer uid);

    WorkerPropertyVO changeUserProfile(WorkerPropertyVO workerPreferVO);

    WorkerPropertyVO setUserProfile(WorkerPropertyVO workerPreferVO);

    WorkerPropertyVO getWorkerProperty(Integer workerId);

    WorkerPropertyVO increaseActivity(Integer workerId, Double activity);

    boolean increaseTaskCount(Integer workerId);

    boolean increaseBugReportCount(Integer workerId);

    List<UserRankVO> getRankList();
}

