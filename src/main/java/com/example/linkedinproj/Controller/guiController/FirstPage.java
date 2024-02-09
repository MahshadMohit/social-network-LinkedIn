package com.example.linkedinproj.Controller.guiController;

import com.example.linkedinproj.Controller.UserController;
import com.example.linkedinproj.LinkedInApplication;
import com.example.linkedinproj.User1;
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
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FirstPage {
    public static User user;
    public UserController userController;

    {
        try {
            userController = new UserController();
        } catch (IOException | java.text.ParseException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void login() {
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
            user = UserController.login(id.getText());

        });
    }

    public void signup() {
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
        TextField dateOfBirth = new TextField();
        dateOfBirth.setPromptText("dateOfBirth");
        TextField universityLocation = new TextField();
        universityLocation.setPromptText("universityLocation");
        TextField field = new TextField();
        field.setPromptText("field");
        TextField workplace = new TextField();
        workplace.setPromptText("workplace");
        TextField specialties = new TextField();
        specialties.setPromptText("separate with comma");
        TextField connectionId = new TextField();
        connectionId.setPromptText("separate with comma");


        grid.add(new Label("Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("dataOfBirth:"),0,2);
        grid.add(dateOfBirth,1,2);
        grid.add(new Label("universityLocation"),0,3);
        grid.add(universityLocation,1,3);
        grid.add(new Label("field"),0,4);
        grid.add(field,1,4);
        grid.add(new Label("workPlace"),0,5);
        grid.add(workplace,1,5);
        grid.add(new Label("specialties"),0,6);
        grid.add(specialties,1,6);
        grid.add(new Label("connectionId"),0,7);
        grid.add(connectionId,1,7);



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
            List<String> items1 = Arrays.asList(specialties.getText().split("\\s*,\\s*"));
            List<String> items = Arrays.asList(connectionId.getText().split("\\s*,\\s*"));
            //user = UserController.signup(id.getText(),name.getText(),dateOfBirth.getText(),universityLocation.getText(),field.getText(),workplace.getText(),
                    //items1,items);

        });
    }

    public void setLogin(ActionEvent e) throws IOException {
        login();
        FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource("user1.fxml"));
        User1.user = user;
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setSignup(ActionEvent e) throws IOException {
        signup();
        FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource("user1.fxml"));
        User1.user = user;
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
