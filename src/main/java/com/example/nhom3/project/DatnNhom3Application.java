package com.example.nhom3.project;

import com.example.nhom3.project.config.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(TelegramProperties.class)
public class DatnNhom3Application {

    public static void main(String[] args) {
        SpringApplication.run(DatnNhom3Application.class, args);
    }


}