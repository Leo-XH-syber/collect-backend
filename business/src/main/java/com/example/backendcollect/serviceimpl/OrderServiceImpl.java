package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.enums.errorcode.Impl.TaskStatusCode;
import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.enums.types.TaskState;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.po.Order;
import com.example.backendcollect.po.Task;
import com.example.backendcollect.service.OrderService;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.Order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    TaskMapper taskMapper;

    private ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }

    @Override
    public OrderVO queryOrder(Integer uid, Integer taskId) {
        Order order = orderMapper.selectByWorkerIdAndTaskId(uid, taskId);
        if (order != null) {
            return new OrderVO(order);
        }
        throw new ServiceException(TaskStatusCode.HE_DIDNT_SELECT_IT);
    }

    // TODO 超卖问题以及更新丢失
    @Override
    public OrderVO selectTask(Integer uid, Integer taskid) {
        Task task = taskMapper.selectByPrimaryKey(taskid);
        if (task == null) {
            throw new ServiceException(TaskStatusCode.HE_DIDNT_SELECT_IT);
        }

        Order order = orderMapper.selectByWorkerIdAndTaskId(uid, taskid);
        if (order != null) {
            throw new ServiceException(TaskStatusCode.HE_HAS_SELECTED_IT);
        }

        OrderVO orderVO = new OrderVO();
        orderVO.setWorkerId(uid);
        orderVO.setTaskId(taskid);
        orderVO.setSelectTime(new Date());
        orderVO.setOrderState(OrderState.IN_PROCESS);
        int orderId = orderMapper.insert(new Order(orderVO));
        orderVO.setId(orderId);

        //已选人数加一
        task.setHasWorkers(task.getHasWorkers() + 1);
        if (task.getHasWorkers().equals(task.getNeedWorkers())) {
            task.setTaskState(TaskState.PERSON_FULL.getCode());
        }

        taskMapper.updateByPrimaryKey(task);

        return orderVO;
    }
}
