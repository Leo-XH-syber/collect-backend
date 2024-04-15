package com.example.backendcollect.controller.user;

import com.example.backendcollect.auth.annotation.Authentication;
import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.UserStatusCode;
import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.service.OrderService;
import com.example.backendcollect.service.UserService;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.utils.validation.Uid;
import com.example.backendcollect.vo.Order.OrderVO;
import com.example.backendcollect.vo.ResultVO;
import com.example.backendcollect.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private OrderService orderService;

    private ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }


    @PostMapping("/login")
    public ResultVO<UserVO> login(@RequestBody @Valid @NotNull UserFormVO userForm) {
        UserVO userVO = userService.userLogin(userForm.getEmail(), userForm.getPassword());
        return resultHelper.success(userVO);
    }

    @Authentication
    @PostMapping("/logout")
    public ResultVO<UserVO> logout(@Uid @RequestParam Integer uid) {
        UserVO userVO = userService.userLogout(uid);
        return resultHelper.success(userVO);
    }

    @PostMapping("/register")
    public ResultVO<UserAllInfoVO> register(@RequestBody @Valid @NotNull UserAllInfoVO userProfileVO) {
        UserVO user = userProfileVO.generateUserVO();
        UserVO userVO = userService.userRegister(user);
        if (userVO.getUserRole().equals(UserRole.WORKER)) {
            userProfileVO.setUid(userVO.getId());
            userProfileVO.setWorkerId(userVO.getId());
            WorkerPropertyVO workerPreferVO = userService.setUserProfile(userProfileVO.generateWorkerPreferVO());
            return resultHelper.success(new UserAllInfoVO(userVO, workerPreferVO));
        } else {
            return resultHelper.success(new UserAllInfoVO(userVO));
        }
    }

    @PostMapping("/changeProfile")
    public ResultVO<UserAllInfoVO> changeUserProfile(@RequestBody UserAllInfoVO userProfileVO) {
        UserVO userVO = userService.getUser(userProfileVO.getUid());
        WorkerPropertyVO workerPreferVO = userProfileVO.generateWorkerPreferVO();
        workerPreferVO = userService.changeUserProfile(workerPreferVO);
        return resultHelper.success(new UserAllInfoVO(userVO, workerPreferVO));
    }

    @GetMapping("/{uid}")
    public ResultVO<UserAllInfoVO> getUser(@PathVariable Integer uid) {
        UserVO user = userService.getUser(uid);
        WorkerPropertyVO workerPrefer = userService.getWorkerProperty(uid);
        return resultHelper.success(new UserAllInfoVO(user, workerPrefer));
    }

    @Authentication
    @GetMapping("/userTaskInfo/{uid}")
    public ResultVO<OrderVO> userTaskInfo(@Uid @PathVariable Integer uid, @RequestParam Integer taskId) {
        OrderVO orderVO = orderService.queryOrder(uid, taskId);
        return resultHelper.success(orderVO);
    }

    //    @Authentication
    @GetMapping("/workerAttribute/{uid}")
    public ResultVO<WorkerPropertyVO> workerAttribute(@PathVariable Integer uid) {
        WorkerPropertyVO workerPropertyVO = userService.getWorkerProperty(uid);
        if (workerPropertyVO != null)
            return resultHelper.success(workerPropertyVO);
        return resultHelper.fail(UserStatusCode.WORKER_NOT_EXIST);
    }

    @GetMapping("/getWorkerRank")
    public List<UserRankVO> getWorkerRank() {
        List<UserRankVO> rankList = userService.getRankList();
        return rankList;
    }
}
