package com.atheima.consumer.workqueue;

import com.atheima.consumer.DmbjzConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年08月30日 9:24
 */
public class WorkQueueConsumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2.设置参数
        factory.setHost("139.196.139.232");
        factory.setPort(5672);
        // 虚拟机
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("123456");
        // 3.创建连接 connection
        Connection connection = factory.newConnection();
        // 4.创建Channel
        Channel channel = connection.createChannel();
        // 5.创建队列Queue
        channel.queueDeclare("q1", true, false, false, null);
        // 6.接收消息
        String body = "hello,消息已经被接收";
        channel.basicConsume("q1", true, new DmbjzConsumer(channel));
    }
}
