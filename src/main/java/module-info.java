module com.example.linkedinproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;



    opens com.example.linkedinproj to javafx.fxml;
    exports com.example.linkedinproj;
    exports com.example.linkedinproj.Controller;
    opens com.example.linkedinproj.Controller to javafx.fxml;
    exports com.example.linkedinproj.Controller.guiController;
    opens com.example.linkedinproj.Controller.guiController to javafx.fxml;
}