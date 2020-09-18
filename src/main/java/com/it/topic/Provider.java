package com.it.topic;

import com.it.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author ch
 * @date 2020-9-18
 * @description
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        String exchangeName = "topics";

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "topic");
        String routingKey = "user.save";
        channel.basicPublish(exchangeName, routingKey, null, ("这是动态路由模式（topic）发送的消息，routingKey为[" + routingKey + "]").getBytes());
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);

    }
}
