package com.it.basic.topic;

import com.it.basic.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author ch
 * @date 2020-9-18
 * @description
 */
public class Consumer2 {

    public static void main(String[] args) throws IOException {
        String exchangeName = "topics";

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "topic");
        String queueName = channel.queueDeclare().getQueue();
        // *表示通配一个  #表示通配0个或1个或多个
        channel.queueBind(queueName, exchangeName, "user.#"); // #.user.#  #.user  #.user.*   ....
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body));
            }
        });

    }
}
