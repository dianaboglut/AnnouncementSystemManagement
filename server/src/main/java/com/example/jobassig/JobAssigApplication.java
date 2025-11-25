package com.example.jobassig;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobAssigApplication {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    public static void main(String[] args) {

        SpringApplication.run(JobAssigApplication.class, args);
    }

    @PostConstruct
    public void logJdbcUrl() {
        System.out.println("▶ spring.datasource.url = " + jdbcUrl);
    }
}
