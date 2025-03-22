package com.example.enrollmentapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StudentController {
    @FXML
    private Button btnBack2;
    @FXML
    private ComboBox<String> cbSelectCourse;
    @FXML
    private ListView<String> lvStudentsinCourse;
    @FXML
    private Pane StudentPane;

    private DatabaseHandler dbHandler;

    @FXML
    public void initialize() {
        dbHandler = new DatabaseHandler();
        loadCourses();
    }

    private void loadCourses() {
        cbSelectCourse.getItems().clear();
        cbSelectCourse.getItems().addAll(dbHandler.getAllCourses());
        cbSelectCourse.setOnAction(event -> loadStudentsInCourse());
    }

    private void loadStudentsInCourse() {
        String selectedCourse = cbSelectCourse.getValue();
        if (selectedCourse == null) return;

        List<String> students = dbHandler.getStudentsInCourse(selectedCourse);

        if (students.isEmpty()) {
            lvStudentsinCourse.getItems().setAll("(Empty. No student/s enrolled in this course.)");
        } else {
            students.sort(String::compareTo);
            for (int i = 0; i < students.size(); i++) {
                students.set(i, (i + 1) + " - " + students.get(i));
            }
            lvStudentsinCourse.getItems().setAll(students);
        }
    }

    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) StudentPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 600);
        stage.setTitle("Enrollment App");
        stage.setScene(scene);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
