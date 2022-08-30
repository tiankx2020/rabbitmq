package com.atheima.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年08月29日 21:55
 */
public class DmbjzConsumer extends DefaultConsumer {

    public DmbjzConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        System.out.println("消费者进行取消消费操作!");
    }

    /* 正常的消息消费
     *   参数一: 消费标签
     *   参数二: Envelope，与消息应答有关
     *   参数三：消息配置
     *   参数四：消息内容
     * */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        System.out.println(sdf.format(new Date()));
        // System.out.println("消费者对消息进行消费!");
        // System.out.println("消费者接收到的consumerTag为: "+consumerTag);
        // System.out.println("消费者接收到的envelope为: "+envelope);
        // System.out.println("消费者接收到的信息配置为: "+properties);
        System.out.println("消费者接收到的信息为: "+new String(body));
        super.getChannel().basicAck(envelope.getDeliveryTag(),false);     //手动消息应答
    }

}
