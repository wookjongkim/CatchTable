package com.project.catchtable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CatchTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatchTableApplication.class, args);
    }
}
