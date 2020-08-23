package com.lr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lr.mapper")
public class TimaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimaoApplication.class, args);
    }

}
