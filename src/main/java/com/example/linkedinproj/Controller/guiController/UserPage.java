package com.example.linkedinproj.Controller.guiController;

import com.example.linkedinproj.Controller.UserController;
import com.example.linkedinproj.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UserPage implements Initializable {
    public static User user;

    @FXML
    private Button back;

    @FXML
    private ImageView choosePic;

    @FXML
    private ImageView edit;

    @FXML
    private ImageView edit1;

    @FXML
    private Button follow1;

    @FXML
    private Button follow10;

    @FXML
    private Button follow2;

    @FXML
    private Button follow3;

    @FXML
    private Button follow4;

    @FXML
    private Button follow5;

    @FXML
    private Button follow6;

    @FXML
    private Button follow7;

    @FXML
    private Button follow8;

    @FXML
    private Button follow9;

    @FXML
    private Rectangle homepageRec;

    @FXML
    private Text homepageText;

    @FXML
    private MenuItem item1;

    @FXML
    private MenuItem item2;

    @FXML
    private Text jobAndUni;

    @FXML
    private Button likeBtn;

    @FXML
    private Text message;

    @FXML
    private Rectangle messageRec;

    @FXML
    private Rectangle networkRec;

    @FXML
    private Text networkText;

    @FXML
    private Button next;

    @FXML
    private Circle pic1;

    @FXML
    private Circle pic10;

    @FXML
    private Circle pic2;

    @FXML
    private Circle pic3;

    @FXML
    private Circle pic4;

    @FXML
    private Circle pic5;

    @FXML
    private Circle pic6;

    @FXML
    private Circle pic7;

    @FXML
    private Circle pic8;

    @FXML
    private Circle pic9;

    @FXML
    private Button postButton;

    @FXML
    private ImageView postPic;

    @FXML
    private ImageView postPic2;

    @FXML
    private TextField postTF;

    @FXML
    private TextArea postTF2;

    @FXML
    private ImageView profile;

    @FXML
    private ImageView profile2;

    @FXML
    private ImageView profile3;

    @FXML
    private TextField search;

    @FXML
    private Text username1;

    @FXML
    private Text username10;

    @FXML
    private Text username2;

    @FXML
    private Text username3;

    @FXML
    private Text username4;

    @FXML
    private Text username5;

    @FXML
    private Text username6;

    @FXML
    private Text username7;

    @FXML
    private Text username8;

    @FXML
    private Text username9;

    @FXML
    private Text usernameSecond;

    @FXML
    private Text usernameText;
    public void setPost(ActionEvent actionEvent) {
        UserController.posts.add(postTF.getText());
        UserController.images.add(postPic.getImage());
        addPost();

    }
    public void addPost(){
        postTF2.setText(UserController.posts.get(UserController.posts.size()-1));
        postPic2.setImage(UserController.images.get(UserController.images.size()-1));

    }
    public void setPostPic(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            postPic.setImage(image);
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameText.setText(user.getName());
        if (user.getWorkplace()== null){
            jobAndUni.setText(user.getUniversityLocation());
        }else {
            jobAndUni.setText(user.getWorkplace() + " " + user.getUniversityLocation());
        }

        if (user.getImage() != null){
            profile.setImage(user.getImage());
            profile3.setImage(user.getImage());
        }

    }



}
