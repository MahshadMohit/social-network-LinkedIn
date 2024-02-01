package com.example.linkedinproj.Controller.guiController;

import com.example.linkedinproj.Controller.UserController;
import com.example.linkedinproj.LinkedInApplication;
import com.example.linkedinproj.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class FirstPage {
    public static User user;

    public void login(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Register");
        dialog.setHeaderText("set your username and password");
        ButtonType login = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(login, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField id = new TextField();
        id.setPromptText("Id");
        TextField name = new TextField();
        name.setPromptText("Name");



        grid.add(new Label("Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(name, 1, 1);




        Node loginButton = dialog.getDialogPane().lookupButton(login);
        loginButton.setDisable(true);
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> id.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == login) {
                return new Pair<>(id.getText(), name.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            user = UserController.login(id.getText(),name.getText());

        });
    }
    public void signup(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Register");
        dialog.setHeaderText("set your username and password");
        ButtonType login = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(login, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField id = new TextField();
        id.setPromptText("Id");
        TextField name = new TextField();
        name.setPromptText("Name");
        DatePicker dateOfBirth = new DatePicker();




        grid.add(new Label("Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(name, 1, 1);




        Node loginButton = dialog.getDialogPane().lookupButton(login);
        loginButton.setDisable(true);
        id.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> id.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == login) {
                return new Pair<>(id.getText(), name.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            user = UserController.login(id.getText(),name.getText());

        });
    }

    public void setLogin(ActionEvent e) throws IOException {
        login();
        FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource("userPage.fxml"));
        UserPage.user = user;
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
