package ru.clevertec.ecl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.clevertec.ecl.repository")
public class EclApplication {

    public static void main(String[] args) {
        SpringApplication.run(EclApplication.class);
    }

}
