package com.music.consumers.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * RocketMQ消费者，没有springBoot的继承，可直接写在java类里面
 */
//@Component
public class RocketMQConsumers {

    /**
     * 生产者所属的组
     */
    private String producerGroup = "integral_group";
    /**
     * MQ的地址，注意需开放端口号或者关闭防火墙
     */
    private String nameServerAddr = "127.0.0.1:9876";
    /**
     * 订阅主题
     */
    private String topic = "integral_topic";
    private DefaultMQPushConsumer consumer;

    public RocketMQConsumers() throws MQClientException {
        consumer = new DefaultMQPushConsumer(producerGroup);
        //指定NameServer地址，多个地址以;隔开
        //如 producer.setNamesrvAddr("192.168.199.100:9876;192.168.199.101:9876;192.168.199.102:9876")
        consumer.setNamesrvAddr(nameServerAddr);
        //设置消费地点，从最后一个开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题，监听主题下的那些标签
        consumer.subscribe(topic, "*");
        //注解一个监听器
        //lambda方式
//        consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
//            try {
//                Message message = msg.get(0);
//                System.out.printf("%s Receive New Messages: %s %n",
//                        Thread.currentThread().getName(), new String(msg.get(0).getBody()));
//                //主题
//                String topic = message.getTopic();
//                //消息内容
//                String body = null;
//                body = new String(message.getBody(), "utf-8");
//                //二级分类
//                String tags = message.getTags();
//                //键
//                String keys = message.getKeys();
//                System.out.println("topic=" + topic + ", tags=" + tags + ", keys=" + keys + ", msg=" + body);
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//            }
//        });

        //一般方式
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    Message message = list.get(0);
                    System.out.printf("%s Receive New Messages: %s %n",
                            Thread.currentThread().getName(), new String(list.get(0).getBody(),"utf-8"));
                    //主题
                    String topic = message.getTopic();
                    //消息内容
                    String body = null;
                    body = new String(message.getBody(), "utf-8");
                    //二级分类
                    String tags = message.getTags();
                    //键
                    String keys = message.getKeys();
                    System.out.println("topic=" + topic + ", tags=" + tags + ", keys=" + keys + ", msg=" + body);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        consumer.start();
        System.out.println("consumer start ..........");
    }

}
