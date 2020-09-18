package com.it.fanout;

import com.it.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author ch
 * @date 2020-9-18
 * @description
 */
public class Consumer3 {
    public static void main(String[] args) throws IOException {
        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 通道绑定到交换机 参数1： 交换机名称  参数2：交换机类型  这两参数应当与生产都创建交换机时的参数一模一样
        channel.exchangeDeclare("logs", "fanout");
        // 创建临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 将临时队列绑定到exchange
        // 参数1：临时队列名称 参数2：交换机 参数3：routingKey，此参数在fanout模式中没有意义，所以为空
        channel.queueBind(queueName,"logs","");
        // 处理消息 参数1：要消费的临时队列  参数2：是否自动确认
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者3： "+ new String(body));
            }
        });

    }
}
