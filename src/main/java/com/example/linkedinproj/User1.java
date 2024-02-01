package com.example.linkedinproj;

import com.example.linkedinproj.Controller.UserController;
import com.example.linkedinproj.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

public class User1 implements Initializable {
    public static User user;
    public ImageView postPicforPost;
    public Text usernamePic;
    public TextField comment;
    @FXML
    private VBox vbox;
    @FXML
    private ScrollPane pane;

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
    @FXML
    private ImageView profile0, profile1, profile2, profile3, profile4, profile5, profile6, profile7, profile8, profile9;
    private List<String> listPrefer = new ArrayList<>();
    @FXML
    private Text conUser;
    public Image[] images = new Image[10];
    public Button[] buttons = new Button[10];
    private UserController controller;

    {
        try {
            controller = new UserController();
        } catch (IOException | org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
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
        if ((!specialtiChoice.isSelected()) && (!fieldChoice.isSelected()) && (!workChoice.isSelected()) && (!uniChoice.isSelected())) {
            listPrefer = controller.suggestionList(user.getId());
            listPrefer.remove(0);
        }
        prev();
    }

    public void setNext() {
        if ((!specialtiChoice.isSelected()) && (!fieldChoice.isSelected()) && (!workChoice.isSelected()) && (!uniChoice.isSelected())) {

            listPrefer = controller.suggestionList(user.getId());
            listPrefer.remove(0);
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
        setImages(listPrefer.get(0), profile0);
        setImages(listPrefer.get(1), profile1);
        setImages(listPrefer.get(2), profile2);
        setImages(listPrefer.get(3), profile3);
        setImages(listPrefer.get(4), profile4);
        setImages(listPrefer.get(5), profile5);
        setImages(listPrefer.get(6), profile6);
        setImages(listPrefer.get(7), profile7);
        setImages(listPrefer.get(8), profile8);
        setImages(listPrefer.get(9), profile9);
    }

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

    public void setArrayFollows() {
        buttons[0] = follow1;
        buttons[1] = follow2;
        buttons[2] = follow3;
        buttons[3] = follow4;
        buttons[4] = follow5;
        buttons[5] = follow6;
        buttons[6] = follow7;
        buttons[7] = follow8;
        buttons[8] = follow9;
        buttons[9] = follow10;

    }

    public void follow(Button button) {
        setArrayFollows();
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getId().equals(button.getId())) {
                controller.follow(user, UserController.map.get(listPrefer.get(i)));
            }
        }

    }

    public void followed(Button b) {
        b.setText("followed");
        b.setBackground(Background.fill(Color.GREEN));
        conUser.setText(String.valueOf(user.getConnectionId().size()));
    }

    public void setFollow1(ActionEvent actionEvent) {
        follow(follow1);
        followed(follow1);
    }

    public void setFollow2(ActionEvent actionEvent) {
        follow(follow2);
        followed(follow2);

    }

    public void setFollow3(ActionEvent actionEvent) {
        follow(follow3);
        followed(follow3);
    }

    public void setFollow4(ActionEvent actionEvent) {
        follow(follow4);
        followed(follow4);
    }

    public void setFollow5(ActionEvent actionEvent) {
        follow(follow5);
        followed(follow5);
    }

    public void setFollow6(ActionEvent actionEvent) {
        follow(follow6);
        followed(follow6);
    }

    public void setFollow7(ActionEvent actionEvent) {
        follow(follow7);
        followed(follow7);
    }

    public void setFollow8(ActionEvent actionEvent) {
        follow(follow8);
        followed(follow8);
    }

    public void setFollow9(ActionEvent actionEvent) {
        follow(follow9);
        followed(follow9);
    }

    public void setFollow10(ActionEvent actionEvent) {
        follow(follow10);
        followed(follow10);
    }

    public void seeConnections(MouseEvent mouseEvent) {
        makeConnectionsVisible();
        pane.setVisible(true);

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
            vbox.getChildren().add(new Text(u.getId()));
        }
    }
    public void setExit(MouseEvent mouseEvent) {
        pane.setVisible(false);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameText.setText(user.getName());
        if (user.getWorkplace() == null) {
            jobAndUni.setText(user.getUniversityLocation());
        } else {
            jobAndUni.setText(user.getWorkplace() + " " + user.getUniversityLocation());
        }
        conUser.setText(String.valueOf(user.getConnectionId().size()));
        setImages(user.getId(), profile);
        setPrev();


    }
}
