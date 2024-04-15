package com.example.backendcollect.mq;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author: https://www.cnblogs.com/haixiang/p/10966985.html
 * @date: 2022/3/4 上午1:29
 * @description
 */

@Component
public class MQSender {

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private RabbitTemplate rabbitTemplate;


    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.out.println("correlationData: " + correlationData);
        System.out.println("ack: " + ack);
        if (!ack) {
            System.out.println("异常处理....");
        }
    };

    final RabbitTemplate.ReturnsCallback returnCallback = System.out::println;


    public void sendLazy(Object message, long milliseconds) {
        int delayTime = milliseconds <= Integer.MAX_VALUE ? (int) milliseconds : Integer.MAX_VALUE;
//        int delayTime = milliseconds <= 10000?(int) milliseconds:10000;
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnCallback);
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("12345678909" + new Date());

        //发送消息时指定 header 延迟时间
        rabbitTemplate.convertAndSend(MQConfig.LAZY_EXCHANGE, "lazy.boot", message,
                message1 -> {
                    //设置消息持久化
                    message1.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    message1.getMessageProperties().setDelay(delayTime);
                    return message1;
                }, correlationData);
    }
}
