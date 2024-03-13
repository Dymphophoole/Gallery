module com.example.galleries {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.galleries to javafx.fxml;
    exports com.example.galleries;
}