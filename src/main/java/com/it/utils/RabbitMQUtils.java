package com.it.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * @author ch
 * @date 2020-9-18
 * @description
 */
public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;
    private static Properties properties;

    static {
        // ConnectionFactory是重量级资源 所以类加载执行只执行一次
        // 创建连接mq的连接工厂对象
        connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq主机
        connectionFactory.setHost("39.98.174.58");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
    }

    // 定义提供连接对象的方法
    public static Connection getConnection() {
        try {
            // 获取连接对象
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 关闭通道和关闭连接工具方法
    public static void closeConnectionAndChannel(Channel channel, Connection connection) {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
