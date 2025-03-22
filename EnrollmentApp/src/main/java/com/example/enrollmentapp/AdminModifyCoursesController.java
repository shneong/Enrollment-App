package com.example.enrollmentapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminModifyCoursesController {

    @FXML
    private Pane enrollmentPane;

    @FXML
    private TextField tfInputCourse, tfUpdateSelectedCourseInto;

    @FXML
    private Button btnConfirmUpdate, btnRemoveSelected, btnAddCourse, btnBackToHome;

    @FXML
    private ListView<String> enrolledList;

    @FXML
    private ToggleButton tbnightMode;

    private DatabaseHandler databaseHandler;
    private ObservableList<String> courseList;

    public void initialize() {
        databaseHandler = new DatabaseHandler();
        loadCourses();
    }


    private void loadCourses() {
        List<String> courses = databaseHandler.getAllCourses();
        courseList = FXCollections.observableArrayList(courses);
        enrolledList.setItems(courseList);
    }

    @FXML
    private void onAddCourseButtonClicked() {
        String courseName = tfInputCourse.getText().trim();

        if (courseName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter a course name.");
            return;
        }

        if (courseList.contains(courseName)) {
            showAlert(Alert.AlertType.ERROR, "Duplicate Course", "This course already exists.");
            return;
        }

        databaseHandler.addCourse(courseName);
        courseList.add(courseName);
        tfInputCourse.clear();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully.");
    }


    @FXML
    private void onRemoveSelectedButtonClicked() {
        String selectedCourse = enrolledList.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a course to remove.");
            return;
        }

        databaseHandler.removeCourse(selectedCourse);
        courseList.remove(selectedCourse);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Course removed successfully.");
    }


    @FXML
    private void onConfirmUpdateButtonClicked() {
        String selectedCourse = enrolledList.getSelectionModel().getSelectedItem();
        String newCourseName = tfUpdateSelectedCourseInto.getText().trim();

        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a course to update.");
            return;
        }

        if (newCourseName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter a new course name.");
            return;
        }

        if (courseList.contains(newCourseName)) {
            showAlert(Alert.AlertType.ERROR, "Duplicate Course", "This course name already exists.");
            return;
        }

        databaseHandler.updateCourse(selectedCourse, newCourseName);
        courseList.set(courseList.indexOf(selectedCourse), newCourseName);
        tfUpdateSelectedCourseInto.clear();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully.");
    }


    @FXML
    public void onNightButtonClicked() {
        Scene scene = enrollmentPane.getScene();
        String nightMode = getClass().getResource("day_mode.css").toExternalForm();

        if (tbnightMode.isSelected()) {
            if (!scene.getStylesheets().contains(nightMode)) {
                scene.getStylesheets().add(nightMode);
            }
            tbnightMode.setText("DAY MODE");
        } else {
            scene.getStylesheets().remove(nightMode);
            tbnightMode.setText("NIGHT MODE");
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) enrollmentPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-choice-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 600);
        stage.setTitle("Admin Access");
        stage.setScene(scene);
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
