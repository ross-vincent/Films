package com.rossbv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmsApp {
    public static void main(String[] args) {       
        // Run the web server
        SpringApplication.run(FilmsApp.class, args);
    }
}
