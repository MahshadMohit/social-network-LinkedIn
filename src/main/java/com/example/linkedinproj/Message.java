package com.example.linkedinproj;

import com.example.linkedinproj.Controller.UserController;
import com.example.linkedinproj.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;

public class Message implements Initializable {

    @FXML
    private ImageView commentImage;

    @FXML
    private Label commentLabel;

    @FXML
    private Text commentText;

    @FXML
    private VBox ferestande;

    @FXML
    private VBox girande;

    @FXML
    private HBox hbox;

    @FXML
    private Rectangle homepageRec;

    @FXML
    private Text homepageText;

    @FXML
    private ImageView linkedin;

    @FXML
    private Text message;

    @FXML
    private Rectangle messageRec;

    @FXML
    private Text name1;

    @FXML
    private Text name2;

    @FXML
    private Text name3;

    @FXML
    private Rectangle networkRec;

    @FXML
    private Text networkText;

    @FXML
    private ScrollPane pane;

    @FXML
    private ImageView postPicforPost;

    @FXML
    private ImageView prof1;

    @FXML
    private ImageView prof2;

    @FXML
    private ImageView prof3;

    @FXML
    private Button send;

    @FXML
    private TextField tf;

    @FXML
    private ToggleButton toggle;

    @FXML
    private Text usernamePic;

    @FXML
    private VBox vbox;
    public List<String> Fchat1 = new ArrayList<>();
    public List<String> Fchat2 = new ArrayList<>();
    public List<String> Fchat3 = new ArrayList<>();
    public List<String> Gchat1 = new ArrayList<>();
    public List<String> Gchat2 = new ArrayList<>();
    public List<String> Gchat3 = new ArrayList<>();
    public User user1,user2,user3;
    public Image[] images = new Image[10];

    public void setImages(String id, ImageView view) {
        setArrayImages();
        int r = Integer.parseInt(id) % 10;
        view.setImage(images[r]);
    }

    public void setArrayImages() {
        images[0] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\0.jpg");
        images[1] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\1.jpeg");
        images[2] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\2.jpeg");
        images[3] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\3.jpeg");
        images[4] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\4.jpeg");
        images[5] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\5.jpeg");
        images[6] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\6.jpeg");
        images[7] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\7.jpeg");
        images[8] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\8.jpg");
        images[9] = new Image("C:\\Users\\asus\\IdeaProjects\\LinkedInProj\\src\\main\\resources\\com\\9.jpg");

    }

    public User setChat(User nahaee){
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

        });
        nahaee = UserController.map.get(id.getText());
        return nahaee;
    }

    public void setting(List<String> fchat , List<String> gchat,User user,ImageView img){

        if (fchat.isEmpty() || gchat.isEmpty()){
            user = setChat(user);
            setImages(user.getId(),img);
            tf.setVisible(true);
        }else{
            for (String s : fchat) {
                ferestande.getChildren().add(new Label(s));
            }
            for (String s1 : gchat){
                girande.getChildren().add(new Label(s1));
            }
        }


    }

    public void setChat1(MouseEvent mouseEvent) {

        setting(Fchat1,Gchat1,user1,prof1);

    }

    public void setChat2(MouseEvent mouseEvent) {

        setting(Fchat2,Gchat2,user2,prof2);

    }

    public void setChat3(MouseEvent mouseEvent) {

        setting(Fchat3,Gchat3,user3,prof3);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf.setVisible(false);
    }
}
