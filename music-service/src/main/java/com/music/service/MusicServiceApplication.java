package com.music.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.rocketmq.client.log.ClientLogger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.demo.dao")
@SpringBootApplication
//@EnableScheduling
@EnableDubbo
public class MusicServiceApplication {

    public static void main(String[] args) {
        System.setProperty(ClientLogger.CLIENT_LOG_USESLF4J,"true");
        SpringApplication.run(MusicServiceApplication.class, args);
    }

}
