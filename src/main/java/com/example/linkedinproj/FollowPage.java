package com.example.linkedinproj;

import com.example.linkedinproj.Controller.UserController;
import com.example.linkedinproj.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FollowPage implements Initializable {

    @FXML
    private Text back;

    @FXML
    private ScrollPane pane;

    @FXML
    private ImageView postPicforPost;

    @FXML
    private ImageView profile;

    @FXML
    private TextArea toString;

    @FXML
    private VBox vbox;

    public Image[] images = new Image[10];
    public static User user;
    public static User mainUser;
    public static Image image;

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

    public void makeConnectionsVisible() {
        List<User> connectionsList = new ArrayList<>();
        for (String s : user.getConnectionId()) {
            connectionsList.add(UserController.map.get(s));
        }

        for (User u : connectionsList) {
            ImageView img = new ImageView();
            img.setFitHeight(66.0);
            img.setFitWidth(72.0);
            setImages(u.getId(), img);
            vbox.getChildren().add(img);
            vbox.getChildren().add(new Text(u.getId() + "-" + u.getName()));
        }
    }
    public void setBack(MouseEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LinkedInApplication.class.getResource("user1.fxml"));
        User1.user = mainUser;
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toString.setText(user.toString());
        toString.setWrapText(true);
        setImages(user.getId(), profile);
        makeConnectionsVisible();




    }


}
