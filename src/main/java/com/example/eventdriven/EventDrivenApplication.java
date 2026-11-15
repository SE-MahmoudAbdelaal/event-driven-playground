package com.example.eventdriven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Order Management example.
 *
 * <p>Start it with {@code ./gradlew bootRun} ({@code gradlew.bat bootRun} on Windows)
 * and send a request to {@code POST http://localhost:8080/api/orders}.
 */
@SpringBootApplication
public class EventDrivenApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventDrivenApplication.class, args);
    }
}
