package com.it.test;

import com.it.springboot.RabbitmqLearningApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RabbitmqLearningApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    // 注入rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // hello world
    @Test
    public void test(){
        //  因为hello world 模式没有交换机和路由概念，所以第一个参数代表队列名称，第二个参数代表发送的内容
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("hello","hello world");
        }
    }

    // work 模式
    @Test
    public void testWork(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work","work 模型_"+i);
        }
    }

    // fanout 模式
    @Test
    public void testFanout(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("logs", "", "这是日志广播" + i);
        }
    }
    // route 模式
    @Test
    public void testRoute(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("directs","error","error 的日志信息");
        }
    }

    // topic 模式
    @Test
    public void testTopic(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("topics","user.save.log.log","topic 模式，user.save route发送的消息");
        }
    }
}
