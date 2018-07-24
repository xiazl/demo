package com.fly.caipiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LogAnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogAnalysisApplication.class, args);
    }
}
