package com.leduyminh.filemgtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileMgtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileMgtServiceApplication.class, args);
    }

}
