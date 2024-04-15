package com.example.backendcollect.mapperservice;

import com.example.backendcollect.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    List<Order> selectByWorkerId(@Param("uid") Integer uid, @Param("orderState") Integer orderState);

    List<Order> selectAllByWorkerId(@Param("uid") Integer uid);


    Order selectByWorkerIdAndTaskId(@Param("uid") Integer uid, @Param("taskId") Integer taskId);

    int updateOrderState(@Param("taskId") Integer taskId, @Param("workerId") Integer workerId,
                         @Param("orderState") Integer orderState);

    // 更新某个任务所有未完成订单的状态, 已经完成的不管
    int updateOrderStateInt(@Param("taskId") Integer taskId, @Param("orderState") Integer orderState);

    List<Integer> getWorkerTaskKindsByWorkerId(Integer workerId);

    List<Integer> getWorkerOSKindsByWorkerId(Integer workerId);
}
