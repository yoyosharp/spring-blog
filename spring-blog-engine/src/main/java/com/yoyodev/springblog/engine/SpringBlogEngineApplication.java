package com.yoyodev.springblog.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The only SpringApplication in the project. Scans all three modules
 * explicitly: Spring Boot would only scan this package (engine) by default,
 * so core entities / repositories and common beans need the explicit scans.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.yoyodev.springblog.core",
        "com.yoyodev.springblog.common",
        "com.yoyodev.springblog.engine"
})
@EntityScan(basePackages = "com.yoyodev.springblog.core.entities")
@EnableJpaRepositories(basePackages = {
        "com.yoyodev.springblog.core.repositories",
        "com.yoyodev.springblog.engine.repositories"
})
@EnableJpaAuditing
public class SpringBlogEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogEngineApplication.class, args);
    }

}
