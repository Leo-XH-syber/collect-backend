package com.example.backendcollect.mq;

import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.enums.types.TaskState;
import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.po.Task;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * @author: https://www.cnblogs.com/haixiang/p/10966985.html
 * @date: 2022/3/4 上午1:43
 * @description
 */

@Component
public class MQReceiver {
    @Resource
    TaskMapper taskMapper;
    @Resource
    OrderMapper orderMapper;

    private MQSender mqSender;

    @Autowired
    public void setMqSender(MQSender mqSender) {
        this.mqSender = mqSender;
    }


    @RabbitListener(queues = "MQ.LazyQueue")
    @RabbitHandler
    public void onLazyMessage(Message msg, Channel channel) throws IOException {
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        // 手动应答
        channel.basicAck(deliveryTag, true);
        ByteArrayInputStream in = new ByteArrayInputStream(msg.getBody());
        //将保存在内存中的字节输入反序列化
        try (ObjectInputStream ins = new ObjectInputStream(in);) {
            Task task = (Task) ins.readObject();
            // 时间到了, task过期, 该任务的所有未完成order过期
            long delayTime = task.getEndTime().getTime() - new Date().getTime();

            if (delayTime <= 0) {
                task.setTaskState(TaskState.TIME_OVER.getCode());
                taskMapper.updateByPrimaryKey(task);
                orderMapper.updateOrderStateInt(task.getId(), OrderState.EXPIRED.getCode());
            } else {
                mqSender.sendLazy(task, delayTime);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        taskMapper.updateByPrimaryKey()

    }
}
