package com.jsonformatter;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Main Spring Boot application class that launches the JavaFX application.
 */
@SpringBootApplication
@ComponentScan(
    basePackages = "com.jsonformatter",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {com.jsonformatter.controller.MainController.class}
    )
)
public class JsonFormatterApplication {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        // Launch Spring Boot first
        applicationContext = new SpringApplicationBuilder(JsonFormatterApplication.class)
                .headless(false)
                .run(args);

        // Then launch JavaFX
        Application.launch(JavaFxApplication.class, args);
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
