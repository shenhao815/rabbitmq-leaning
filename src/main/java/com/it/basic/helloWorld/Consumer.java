package com.it.basic.helloWorld;

import com.it.basic.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws IOException{

        // 使用工具获取连接
        Connection connection = RabbitMQUtils.getConnection();

        // 获取连接中通道
        Channel channel = connection.createChannel();

        // 通道绑定队列
        // 注意：此方法参数应当于生产者的此方法参数值一样，如不一样可能会出错
        channel.queueDeclare("hello", false, false, false, null);

        // 消费消息
        // 参数1： 消费哪个队列的消息 队列名称
        // 参数2：开始消息的自动确认机制 true 自动确认，false-不自动确认
        //       所谓自动确认是指：消费者不关心自己的业务有没有处理完，他只要拿到平均分配的消息，
        //       他就告诉队列那些消息已经消费完了，此时队列就把分给它的消息全部删除掉，
        //       但实际上消费者可能还没有处理掉分给它的消息。如果此时该消费者宕机，那么它未处理的消息就丢失了。
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
