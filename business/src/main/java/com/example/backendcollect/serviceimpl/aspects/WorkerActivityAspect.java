package com.example.backendcollect.serviceimpl.aspects;

import com.example.backendcollect.service.UserService;
import com.example.backendcollect.utils.ActivityLevel;
import com.example.backendcollect.vo.Order.OrderVO;
import com.example.backendcollect.vo.report.CoReportVO;
import com.example.backendcollect.vo.report.CriticismVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.example.backendcollect.vo.user.UserVO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class WorkerActivityAspect {
    @Autowired
    UserService userService;

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.UserService.userLogin(..))", returning = "retVal")
    public void updateActivityLogin(Object retVal) {
        UserVO userVO = (UserVO) retVal;
        userService.increaseActivity(userVO.getId(), ActivityLevel.LOGIN_ACTIVITY);
    }

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.ReportService.submitReport(..))", returning = "retVal")
    public void updateActivityCreateReport(Object retVal) {
        ReportVO report = (ReportVO) retVal;
        userService.increaseActivity(report.getWorkerId(), ActivityLevel.CREATE_REPORT_ACTIVITY);
    }

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.ReportService.criticizeReport(..))", returning = "retVal")
    public void updateActivityCreateCriticism(Object retVal) {
        CriticismVO criticismVO = (CriticismVO) retVal;
        userService.increaseActivity(criticismVO.getWorkerId(), ActivityLevel.CREATE_CRITICISM_ACTIVITY);
    }

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.CoReportService.submitReport(..))", returning = "retVal")
    public void updateActivityCreateCoReport(Object retVal) {
        CoReportVO coReportVO = (CoReportVO) retVal;
        userService.increaseActivity(coReportVO.getWorkerId(), ActivityLevel.CREATE_CO_REPORT_ACTIVITY);
    }

    @AfterReturning(pointcut = "execution(* com.example.backendcollect.service.OrderService.selectTask(..))", returning = "retVal")
    public void updateActivitySelectTask(Object retVal) {
        OrderVO orderVO = (OrderVO) retVal;
        userService.increaseActivity(orderVO.getWorkerId(), ActivityLevel.SELECT_TASK_ACTIVITY);
    }


}
