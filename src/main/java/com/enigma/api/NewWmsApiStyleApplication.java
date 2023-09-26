package com.enigma.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NewWmsApiStyleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewWmsApiStyleApplication.class, args);
    }

}
