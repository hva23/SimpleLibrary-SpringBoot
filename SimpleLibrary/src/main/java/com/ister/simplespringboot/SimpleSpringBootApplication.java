package com.ister.simplespringboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ister.repository.jpa.hibernate") //Scanning Jpa Repositories
@ComponentScan(basePackages = "com.ister")      //Scanning @Components, @Services, @Configurations
@EntityScan(basePackages = "com.ister.model")   //Scanning @Entity
public class SimpleSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringBootApplication.class, args);
    }

    @RequestMapping("/")
    public String hello() {
        String msg = "Hello, welcome to library\nyou can add authors via sending a post request to /authors/add URL\nalso to add a book do the same for /books/add URL";
        System.out.println(msg);
        return msg;
    }
}





