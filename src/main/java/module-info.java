module com.example.linkedinproj {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.linkedinproj to javafx.fxml;
    exports com.example.linkedinproj;
}