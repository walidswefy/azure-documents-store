package com.example.sampleapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleAppApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SampleAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
