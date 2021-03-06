package com.example.api.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@SpringBootApplication
@Slf4j
public class StorageServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Application in running state");
    }
}
