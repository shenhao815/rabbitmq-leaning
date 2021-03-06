package com.it.basic.topic;

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
        String exchangeName = "topics";

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "topic");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, "user.*"); // *.user.*  *.*.user   user.*.*   ....
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });

    }
}
