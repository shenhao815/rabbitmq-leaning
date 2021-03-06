package com.it.basic.direct;

import com.it.basic.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author ch
 * @date 2020-9-18
 * @description
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.exchangeDeclare("logs_direct","direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "logs_direct", "error");
        channel.basicConsume(queueName,false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息者1： "+ new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
