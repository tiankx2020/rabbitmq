package com.atheima.producer.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年08月30日 11:08
 */
public class ProducerRouting {
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
        // 5.创建交换机
        // AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, // 交换机名称
        //         BuiltinExchangeType type, // 交换机类型
        // boolean durable, // 是否持久化
        // boolean autoDelete, //是否删除
        // boolean internal,  // 内部使用，一般设置为false
        // Map<String, Object> arguments) // 参数列表
        String exchangeName = "testDirect";
        // BuiltinExchangeType.FANOUT决定了交换机的类型
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);
        // 6.创建队列
        String queueName1 = "testDirectQ1";
        String queueName2 = "testDirectQ2";
        // 队列的声明
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueDeclare(queueName2,true,false,false,null);
        // 7.绑定队列和交换机
        // routingKey 路由键，绑定规则
        channel.queueBind(queueName1,exchangeName,"error");
        channel.queueBind(queueName2,exchangeName,"info");
        channel.queueBind(queueName2,exchangeName,"error");
        channel.queueBind(queueName2,exchangeName,"warning");
        // 8.发送消息
        String body = "日志信息：张三调用了findAll方法... 日志级别为:info ...";
        channel.basicPublish(exchangeName,"error",null,body.getBytes());
        // 9.释放资源
        channel.close();
        connection.close();
    }
}
