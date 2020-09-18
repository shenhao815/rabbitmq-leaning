package com.it.workqueue;

import com.it.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

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
        channel.queueDeclare("work", true, false, false, null);

        for (int i = 0; i < 20; i++) {
            channel.basicPublish("","work", null,(i+" hello work queue").getBytes());
        }

        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
