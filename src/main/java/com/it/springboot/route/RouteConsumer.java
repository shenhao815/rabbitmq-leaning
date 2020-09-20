package com.it.springboot.route;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RouteConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(),
                    key = ("error,info,warn"),
                    exchange = @Exchange(name = "directs",type = "direct")
            )
    })
    public void receive1(String message) {
        System.out.println("message1： " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(),
                    key = ("error"),
                    exchange = @Exchange(name = "directs",type = "direct")
            )
    })
    public void receive2(String message) {

        System.out.println("message2: " + message);
    }
}
