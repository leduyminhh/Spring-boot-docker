package com.leduyminh.metricsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableMongoRepositories
@EnableMongoAuditing
public class MetricsServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetricsServerApplication.class, args);
    }
}
