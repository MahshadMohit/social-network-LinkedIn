package com.example.linkedinproj;

import com.example.linkedinproj.Controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class LinkedInApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource("firstPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1021, 487.0);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            UserController.readUsersFromJSONFile();
        } catch (IOException | ParseException | java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        UserController.setGraphMap();
        //UserController.buildGraph();
        launch();

    }
}