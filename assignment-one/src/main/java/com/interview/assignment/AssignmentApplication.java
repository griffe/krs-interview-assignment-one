package com.interview.assignment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(exclude = {ReactiveUserDetailsServiceAutoConfiguration.class})
@EnableWebFlux
@ConfigurationPropertiesScan
public class AssignmentApplication {

    private static final Logger logger = LogManager.getLogger(AssignmentApplication.class);

    public static void main(String[] args) {
        logger.info("Starting application");

        SpringApplication.run(AssignmentApplication.class, args);

        logger.info("Application started");
    }

}


