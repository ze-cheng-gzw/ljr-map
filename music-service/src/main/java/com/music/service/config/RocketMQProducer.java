package com.music.service.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

/**
 * RocketMQ生产者，没有springBoot的继承，可直接写在java类里面
 */
@Component
public class RocketMQProducer {

    /**
     * 生产者所属的组
     */
    private String producerGroup = "integral_group";

    /**
     * MQ的地址，注意需开放端口号或者关闭防火墙
     */
    private String nameServerAddr = "127.0.0.1:9876";

    private DefaultMQProducer producer;

    public RocketMQProducer() {
        producer = new DefaultMQProducer(producerGroup);
        //指定NameServer地址，多个地址以;隔开
        //如 producer.setNamesrvAddr("192.168.199.100:9876;192.168.199.101:9876;192.168.199.102:9876")
        producer.setNamesrvAddr(nameServerAddr);
        start();
    }

    /**
     * 获取生产者
     * @return
     */
    public DefaultMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 开启，对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭，一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }
}
