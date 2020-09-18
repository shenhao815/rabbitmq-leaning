package com.it.helloWorld;

import com.it.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    @Test
    public void testSendMessage() throws IOException{

       // 使用工具类获取连接
        Connection connection = RabbitMQUtils.getConnection();

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

        /*// 释放资源
        channel.close();
        connection.close();*/

        // 使用工具类关闭
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
