package com.example.backendcollect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.backendcollect.mapperservice")
public class BackendCollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCollectApplication.class, args);
    }

}
