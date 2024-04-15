package com.example.backendcollect.serviceimpl.aspects;

import com.example.backendcollect.serviceimpl.RedisService;
import com.example.backendcollect.vo.Order.OrderVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.example.backendcollect.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Rikka
 * @date 2022-03-26 02:22:16
 * @description 日志用 aop 也是可以的, 不必去业务模块写 log.warn 什么的. 虽然有 @Slf4j 的帮助
 * 也不是很复杂, 但是感觉这样统一一点
 */
@Aspect
@Component
@Slf4j
public class ChoreAspect {
    private final RedisService redisService;

    @Autowired
    public ChoreAspect(RedisService redisService) {
        this.redisService = redisService;
    }

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.UserService.userLogin(..))", returning = "userInfo")
    public void logging(UserVO userInfo) {
        log.info(userInfo.toString());
    }

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.OrderService.selectTask(..))", returning = "orderVO")
    public void taskSelecting(OrderVO orderVO) {
        redisService.lpush("workerId:" + orderVO.getWorkerId(), orderVO.getTaskId());
        log.info(String.format("TASK_SELECTING:{\"workerId\":%d,\"taskId\":%d,\"timestamp\":%d}", orderVO.getWorkerId(), orderVO.getTaskId(), new Date().getTime()));
        log.info(Arrays.toString(redisService.lrange("workerId:" + orderVO.getWorkerId(), 0, 20, Integer.class).toArray()));
    }

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.ReportService.submitReport(..))", returning = "reportVO")
    public void report(ReportVO reportVO) {
        log.info(String.format("REPORT:{\"reportId\":%d,\"taskId\":%d}", reportVO.getId(), reportVO.getTaskId()));
    }
}
