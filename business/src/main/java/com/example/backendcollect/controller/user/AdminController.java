package com.example.backendcollect.controller.user;

import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.service.TaskService;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private TaskService taskService;

    private ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }

    @PostMapping("/setRecommendationType")
    public ResultVO<RecStrategyType> setRecommendStrategy(@NotNull @RequestParam RecStrategyType type) {
        return resultHelper.success(taskService.setRecommendStrategy(type));
    }

    @GetMapping("/getRecommendationType")
    public ResultVO<RecStrategyType> getRecommendStrategy() {
        return resultHelper.success(taskService.getRecommendStrategy());
    }

}
