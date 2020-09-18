package com.it.direct;

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
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs_direct", "direct");
        String routingKey = "info";
        channel.basicPublish("logs_direct",routingKey,null,("指定的route key [ "+routingKey+" ] 发送的消息").getBytes());
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
