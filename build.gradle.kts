plugins {
    java
    // Adds the bootRun / bootJar tasks and knows how to package a Spring Boot application.
    id("org.springframework.boot") version "3.5.16"
    // Imports Spring Boot's list of tested dependency versions (a "BOM"),
    // so the dependencies below don't need version numbers.
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Educational example of Event-Driven Architecture with Spring Application Events"

java {
    toolchain {
        // Compile, test and run with Java 21 - whatever JDK happens to run Gradle itself.
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring MVC + embedded Tomcat + Jackson (JSON).
    // It also brings in spring-context, which contains ApplicationEventPublisher and @EventListener.
    // No message broker (Kafka, RabbitMQ, ...) is needed: events stay inside this application.
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
