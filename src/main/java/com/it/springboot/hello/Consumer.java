package com.it.springboot.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component // 默认是持久化 非独占 不是自动删除队列
@RabbitListener(queuesToDeclare = @Queue(value = "hello",durable = "true",exclusive = "false",autoDelete = "false"))
public class Consumer {
    @RabbitHandler
    public void receive1(String message) {
        System.out.println("message: "+ message);
    }
}
