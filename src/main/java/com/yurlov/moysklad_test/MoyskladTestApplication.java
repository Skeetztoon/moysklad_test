package com.yurlov.moysklad_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.yurlov.moysklad_test.adapter.persistance")
@EntityScan(basePackages = "com.yurlov.moysklad_test.domain")
public class MoyskladTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoyskladTestApplication.class, args);
    }
}
