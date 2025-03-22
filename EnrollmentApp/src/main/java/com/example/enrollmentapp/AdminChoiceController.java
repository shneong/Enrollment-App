package com.example.enrollmentapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminChoiceController {

    public Button btnEnrollStudents;
    public Button btnModifyCourses;
    public Button btnBack;
    public Pane homepage;

    public void onModifyButton(ActionEvent actionEvent) throws IOException {
        switchScene("admin-modify-courses-page.fxml", actionEvent);
    }

    public void onEnrollButton(ActionEvent actionEvent) throws IOException {
        switchScene("admin-enroll-page.fxml", actionEvent);
    }

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        switchScene("home-page.fxml", actionEvent);
    }

    private void switchScene(String fxmlFile, ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) homepage.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 620, 600);
        stage.setTitle("Enrollment App");

        scene.getStylesheets().add(getClass().getResource("button-hover.css").toExternalForm());
        stage.setScene(scene);
    }
}
