package com.demo.controller;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.demo.dao")
@SpringBootApplication
//@EnableScheduling
@EnableDubbo
public class MusicControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicControllerApplication.class, args);
    }

}
