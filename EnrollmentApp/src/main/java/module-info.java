module com.example.enrollmentapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.enrollmentapp to javafx.fxml;
    exports com.example.enrollmentapp;
}