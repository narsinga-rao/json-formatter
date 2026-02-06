package com.jsonformatter;

import com.jsonformatter.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * JavaFX Application that integrates with Spring Boot.
 */
public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = JsonFormatterApplication.getApplicationContext();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            MainController mainController = springContext.getBean(MainController.class);
            Scene scene = new Scene(mainController.getView(), 1200, 700);
            
            primaryStage.setTitle("JSON Formatter");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
    }
}
