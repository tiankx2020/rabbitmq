package com.atheima.consumer.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年08月30日 10:50
 */
public class ConsumerRouting1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接工厂
        ConnectionFactory factory  =new ConnectionFactory();
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
        // channel.queueDeclare("q1",true,false,false,null);
        // 6.接收消息
        Consumer consumer = new DefaultConsumer(channel){
            /**
             * @param consumerTag  消息标识
             * @param envelope   获取一些信息，教官及，路由key
             * @param properties  配置信息
             * @param body    发送的数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
                System.out.println(sdf.format(new Date()));
                // System.out.println("消费者接收到的consumerTag为: "+consumerTag);
                // System.out.println("消费者接收到的envelope为: "+envelope);
                // System.out.println("消费者接收到的信息配置为: "+properties);
                System.out.println("body: "+new String(body));
                System.out.println("将日志信息保存数据库.....");
            }
        };
        String queueName1 = "testFanoutQ1";
        String queueName2 = "testFanoutQ2";
        channel.basicConsume(queueName1,true,consumer);
    }
}
