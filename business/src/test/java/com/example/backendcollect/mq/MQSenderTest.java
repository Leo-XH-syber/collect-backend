package com.example.backendcollect.mq;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MQSenderTest {

    @Autowired
    private MQSender mqSender;

    @Test
    public void sendLazy() throws  Exception {
        String msg = "hello spring boot";

        mqSender.sendLazy(msg + ":", 10000L);
    }
}
