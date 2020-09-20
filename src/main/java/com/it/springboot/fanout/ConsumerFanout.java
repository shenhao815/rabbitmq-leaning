package com.it.springboot.fanout;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class ConsumerFanout {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "logs",type = "fanout")
    ))
    public void receive1(String message){
        System.out.println("message1: "+message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "logs",type = "fanout")
    ))
    public void receive2(String message){
        System.out.println("message2: "+message);
    }
}
