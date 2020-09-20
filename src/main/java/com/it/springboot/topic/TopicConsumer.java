package com.it.springboot.topic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class TopicConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(
                    name = "topics",
                    type = "topic"
            ),
            key = {"user.*","user.*.*" }
    ))
    public void receive1(String msg, Channel channel, Message message) throws IOException {

        try {
            /**
             * 确认一条消息：
             * channel.basicAck(deliveryTag,false)
             * deliveryTag: 该消息的index
             * multiple: 是否批量. true:将一次性ack所有小于deliveryTag的消息
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("message1: " + msg);

        } catch (IOException e) {
            // 消息者处理出了问题，需要告诉队列信息消费失败
            /**
             * 拒绝确认消息
             * channel.basicNack(long deliveryTag,boolean multiple,boolean requeue);
             * deliveryTag: 该消息的index
             * multiple: 是否批量. true: 将一次性拒绝所有小于deliveryTag的消息
             * requeue: 被拒绝的是否重新入队列
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            e.printStackTrace();



            /**
             * 拒绝一条消息
             * channel.basicReject(long deliveryTag,boolean requeue)
             * deliveryTag: 该消息的index
             * requeue: 被拒绝的是否重新入队列
             */
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(
                    name = "topics",
                    type = "topic"
            ),
            key = {"user.#","#.login.#"}
    ))
    public void receive2(String message) {
        System.out.println("message2: " + message);
    }
}
