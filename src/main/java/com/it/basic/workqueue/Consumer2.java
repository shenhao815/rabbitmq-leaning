package com.it.basic.workqueue;

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
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);// 每一次只消费一个消息
        channel.queueDeclare("work", true, false, false, null);
        channel.basicConsume("work", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2： " +new String(body));
                // 当自动确认关闭时，应当进行手动确认
                // 参数1：确认队列中那个具体消息 参数2：是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
