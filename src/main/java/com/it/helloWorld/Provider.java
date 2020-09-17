package com.it.helloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq主机
        connectionFactory.setHost("39.98.174.58");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        // 获取连接对象
        Connection connection = connectionFactory.newConnection();

        // 获取连接中通道
        Channel channel = connection.createChannel();
        // 通道绑定对应消息队列
        // 参数1：队列名称 如果队列不存在自动创建
        // 参数2：用来定义队列特性是否要持久化 true-持久化队列 false-不持久化
        // 参数3：exclusive 当前连接是否独占队列 true-独占队列 false-不独占
        // 参数4：autoDelete: autoDelete:
        // 参数5：额外参数（应该是相当于用户自定义参数）
        channel.queueDeclare("hello", false, false, false, null);

        // 发布消息
        // 参数1：交换机名称 参数2：队列名称 参数3：传递消息额外设置 参数4：消息的具体内容
        channel.basicPublish("","hello",null,"hello rabbitmq".getBytes());

        // 释放资源
        channel.close();
        connection.close();
    }
}
