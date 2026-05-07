package com.openclassroom.mdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.openclassroom.mdd")
public class MddapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MddapiApplication.class, args);
    }
}
