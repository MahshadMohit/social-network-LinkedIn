package com.example.linkedinproj.Controller.guiController;

import com.example.linkedinproj.Controller.UserController;
import com.example.linkedinproj.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserPage implements Initializable {
    public static User user;

    @FXML
    private ImageView choosePic;

    @FXML
    private ImageView edit1;

    @FXML
    private RadioButton fieldChoice;

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
    private Text jobAndUni;

    @FXML
    private Text message;

    @FXML
    private Rectangle messageRec;

    @FXML
    private Rectangle networkRec;

    @FXML
    private Text networkText;

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
    private TextField search;

    @FXML
    private RadioButton specialtiChoice;

    @FXML
    private ToggleButton toggle;

    @FXML
    private RadioButton uniChoice;

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
    private Text usernameText;

    @FXML
    private RadioButton workChoice;
    @FXML
    private Button next;
    @FXML
    private Button prev;
    private List<String> listPrefer = new ArrayList<>();
    private UserController controller;

    {
        try {
            controller = new UserController();
        } catch (IOException | ParseException | org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public void setPost(ActionEvent actionEvent) {
        UserController.posts.add(postTF.getText());
        UserController.images.add(postPic.getImage());
        addPost();

    }

    public void addPost() {
        postTF2.setText(UserController.posts.get(UserController.posts.size() - 1));
        postPic2.setImage(UserController.images.get(UserController.images.size() - 1));

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

    public void setPrev() {
        if ((!specialtiChoice.isSelected()) && (!fieldChoice.isSelected()) && (!workChoice.isSelected()) && (!uniChoice.isSelected())){
            listPrefer = controller.suggestionList(user.getId());
        }
        prev();
    }

    public void setNext() {
        if ((!specialtiChoice.isSelected()) && (!fieldChoice.isSelected()) && (!workChoice.isSelected()) && (!uniChoice.isSelected())){
            listPrefer = controller.suggestionList(user.getId());
        }
        next();
    }

    public void next() {
        username1.setText(listPrefer.get(10));
        username2.setText(listPrefer.get(11));
        username3.setText(listPrefer.get(12));
        username4.setText(listPrefer.get(13));
        username5.setText(listPrefer.get(14));
        username6.setText(listPrefer.get(15));
        username7.setText(listPrefer.get(16));
        username8.setText(listPrefer.get(17));
        username9.setText(listPrefer.get(18));
        username10.setText(listPrefer.get(19));
    }

    public void prev() {
        username1.setText(listPrefer.get(0));
        username2.setText(listPrefer.get(1));
        username3.setText(listPrefer.get(2));
        username4.setText(listPrefer.get(3));
        username5.setText(listPrefer.get(4));
        username6.setText(listPrefer.get(5));
        username7.setText(listPrefer.get(6));
        username8.setText(listPrefer.get(7));
        username9.setText(listPrefer.get(8));
        username10.setText(listPrefer.get(9));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameText.setText(user.getName());
        if (user.getWorkplace() == null) {
            jobAndUni.setText(user.getUniversityLocation());
        } else {
            jobAndUni.setText(user.getWorkplace() + " " + user.getUniversityLocation());
        }
        setPrev();




    }


}
