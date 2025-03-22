package com.example.enrollmentapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    public Button btnEnrollStudents;
    public Button btnModifyCourses;
    public Pane homepage;
    public Button btnAdmin;
    public Button btnStudent;
    public ImageView studentImageView;
    public ImageView adminImageView;
    private AdminEnrollController helloController;


    public void onStudentButtonClicked(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) homepage.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("student-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 640);
        stage.setTitle("Student Access");

        StudentController modifyCourses = fxmlLoader.getController();
        FXMLLoader helloLoader = new FXMLLoader(getClass().getResource("student-page.fxml"));
        AdminEnrollController helloController = helloLoader.getController();

        scene.getStylesheets().add(getClass().getResource("button-hover.css").toExternalForm());
        stage.setScene(scene);
    }

    public void onAdminButtonClicked(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) homepage.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-choice-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 640);
        stage.setTitle("Admin Access");
        scene.getStylesheets().add(getClass().getResource("button-hover.css").toExternalForm());
        stage.setScene(scene);
    }
}
