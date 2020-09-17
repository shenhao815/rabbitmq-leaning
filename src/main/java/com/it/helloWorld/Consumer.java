package com.it.helloWorld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("39.98.174.58");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        // 获取连接对象
        Connection connection = connectionFactory.newConnection();

        // 获取连接中通道
        Channel channel = connection.createChannel();

        // 通道绑定队列
        // 注意：此方法参数应当于生产者的此方法参数值一样，如不一样可能会出错
        channel.queueDeclare("hello", false, false, false, null);

        // 消费消息
        // 参数1： 消费哪个队列的消息 队列名称
        // 参数2：开始消息的自动确认机制
        // 参数3：消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("获取的消息为： "+new String(body));
            }
        });

        // 应当一直监听队列，所以不应该释放资源
//        channel.close();
//        connection.close();
    }
}
