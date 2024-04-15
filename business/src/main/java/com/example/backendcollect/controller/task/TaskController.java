package com.example.backendcollect.controller.task;

import com.example.backendcollect.auth.annotation.Authentication;
import com.example.backendcollect.auth.pojo.JwtInfo;
import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.enums.types.TaskState;
import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.service.FileService;
import com.example.backendcollect.service.OrderService;
import com.example.backendcollect.service.ReportBugService;
import com.example.backendcollect.service.TaskService;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.utils.validation.Uid;
import com.example.backendcollect.vo.Order.OrderVO;
import com.example.backendcollect.vo.ResultVO;
import com.example.backendcollect.vo.bug.BugTableVO;
import com.example.backendcollect.vo.task.TaskVO;
import com.github.pagehelper.PageInfo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@RestController
@Validated
@RequestMapping("/task")
public class TaskController {

    @Resource
    TaskService taskService;

    @Resource
    OrderService orderService;

    @Resource
    FileService fileService;
    @Resource
    ReportBugService reportBugService;

    ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }

    private JwtInfo jwtInfo;

    @Autowired
    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }


    @Authentication(allowedRoles = {UserRole.EMPLOYER})
    @PostMapping("/submit")
    public ResultVO<TaskVO> launchTask(@RequestParam("execFile") @NotNull MultipartFile execFile, @RequestParam("requirementFile") @NotNull MultipartFile reqFile, @Valid TaskVO task) {
//        task.setEmployerId(jwtInfo.getId())
        task.setTestApp(fileService.uploadFile(execFile).getUrl());
        task.setTestDoc(fileService.uploadFile(reqFile).getUrl());
        TaskVO taskVO = taskService.createTask(task);
        return resultHelper.success(taskVO);
    }

    @Authentication
    @GetMapping("/getEmployerTask/{page}")
    public PageInfo<TaskVO> getEmployerTask(@PathVariable Integer page, @RequestParam Integer uid, @RequestParam String type) {
        int taskState = TaskState.valueOf(type).getCode();
        return taskService.getTasksByEmployer(uid, page, taskState);
    }

    @Authentication
    @GetMapping("/getAllValid/{page}")
    public PageInfo<TaskVO> getAllValidTask(@PathVariable Integer page, @Uid @RequestParam Integer uid) {
        return taskService.getAllValidTask(page, uid);
    }
    @Authentication
    @GetMapping("/getAllValidDesc/{page}")
    public PageInfo<TaskVO> getAllValidTaskDesc(@PathVariable Integer page, @Uid @RequestParam Integer uid) {
        return taskService.getAllValidTaskDesc(page, uid);
    }

    @GetMapping("/getTaskHall/{page}")
    public PageInfo<TaskVO> getTaskHall(@PathVariable Integer page) {
        return taskService.getTaskHall(page);
    }

    @Authentication
    @GetMapping("/getPagedWorkerTasks/{page}")
    public PageInfo<TaskVO> getPagedWorkerTasks(@PathVariable Integer page, @Uid @RequestParam Integer uid, @RequestParam String type) {
        int orderState = OrderState.valueOf(type).getCode();
        return taskService.getTasksByWorker(uid, page, orderState);
    }

    @Authentication
    @GetMapping("/getAll/{page}")
    public PageInfo<TaskVO> getAllTask(@PathVariable Integer page) {
        return taskService.getAllTask(page);
    }

    //    @Authentication(allowedRoles = UserRole.WORKER)
    @PostMapping("/select/{taskid}")
    public ResultVO<OrderVO> selectTask(@PathVariable Integer taskid, /*@Uid */@RequestParam Integer uid) {
        OrderVO orderVO = orderService.selectTask(uid, taskid);
        return resultHelper.success(orderVO);
    }

    @GetMapping("/getTaskInfo/{taskId}")
    public ResultVO<TaskVO> getTaskInfo(@PathVariable Integer taskId) {
        TaskVO aTask = taskService.getATask(taskId);
        return resultHelper.success(aTask);
    }

    @Authentication
    @GetMapping("/getRecommended/{uid}")
    public List<TaskVO> getRecommended(@PathVariable Integer uid) {
        return taskService.getRecommendedTask(uid);
    }


    @GetMapping("/getBugList/{taskId}")
    public List<BugTableVO> getBugList(@PathVariable Integer taskId) {
        return reportBugService.getBugList(taskId);
    }


}
