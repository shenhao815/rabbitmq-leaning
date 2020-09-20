package com.it.basic.fanout;

import com.it.basic.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author ch
 * @date 2020-9-18
 * @description fanout模式的生产者
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs", "fanout");
        channel.basicPublish("logs","",null,"fanout message".getBytes());
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
