package com.parkhomenko.ITProg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.log4j.Logger;

@SpringBootApplication
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        LOGGER.debug("Log4j appender configuration is successful!");
        LOGGER.debug("http://localhost:8080");
    }

}
