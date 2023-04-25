package com.vault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(Credential.class)
public class VaultApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(VaultApplication.class);
    @Autowired
    Credential credential;

    public static void main(String[] args) {
        SpringApplication.run(VaultApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("_____________________preperties_____________________");
        logger.info("username: {}", credential.getUsername());
        logger.info("password: {}", credential.getPassword());
    }
}
