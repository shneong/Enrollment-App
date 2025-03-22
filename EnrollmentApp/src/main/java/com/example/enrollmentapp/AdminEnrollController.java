package com.example.enrollmentapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminEnrollController {
    public ImageView nightModeIcon;
    @FXML private Button removeButton;
    @FXML private Label welcomeText;
    @FXML private ListView<String> enrolledList;
    @FXML private TextField nameInput;
    @FXML private Button enrollButton;
    @FXML private ComboBox<String> courseChoice;
    @FXML private Pane enrollmentPane;
    @FXML private ToggleButton tbnight;
    @FXML private Button btnBack1;

    @FXML
    private DatabaseHandler dbHandler;

    @FXML
    public void initialize() {
        dbHandler = new DatabaseHandler();
        loadCoursesFromDatabase();

        nameInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onEnrollButtonClicked();
            }
        });

        loadStudentsFromDatabase();
    }

    private void loadCoursesFromDatabase() {
        courseChoice.getItems().clear();
        courseChoice.getItems().addAll(dbHandler.getAllCourses());
    }

    private void loadStudentsFromDatabase() {
        List<String> students = dbHandler.getAllStudents();
        enrolledList.getItems().clear();
        for (String student : students) {
            enrolledList.getItems().add(student);
        }
        System.out.println("List size: " + enrolledList.getItems().size()); // Debugging statement
    }

    @FXML
    public void onEnrollButtonClicked() {
        if (nameInput.getText().isEmpty() || courseChoice.getValue() == null) {
            showAlert("Error", "Please input all fields!");
            return;
        }
        String name = nameInput.getText();
        String course = courseChoice.getValue();
        String entry = name + " (" + course + ")";

        for (String student : enrolledList.getItems()) {
            if (student.contains(entry)) {
                showAlert("Error", "This student is already enrolled in this course!");
                return;
            }
        }
        dbHandler.addStudent(name, course);
        loadStudentsFromDatabase();
        nameInput.clear();
        courseChoice.setValue(null);
    }

    @FXML
    public void onRemoveButtonClicked() {
        String selectedItem = enrolledList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int index = enrolledList.getItems().indexOf(selectedItem);
            String[] parts = selectedItem.split(" - ", 2);
            if (parts.length < 2) return;

            String[] details = parts[1].split(" \\(");
            String name = details[0];
            String course = details[1].replace(")", "");

            dbHandler.removeStudent(name, course);
            loadStudentsFromDatabase();
        } else {
            showAlert("Error", "No student selected!");
        }
    }

    @FXML
    public void onNightButtonClicked() {
        Scene scene = enrollmentPane.getScene();
        String nightMode = getClass().getResource("day_mode.css").toExternalForm();

        if (tbnight.isSelected()) {
            if (!scene.getStylesheets().contains(nightMode)) {
                scene.getStylesheets().add(nightMode);
            }
            tbnight.setText("DAY MODE");
        } else {
            scene.getStylesheets().remove(nightMode);
            tbnight.setText("NIGHT MODE");
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) enrollmentPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-choice-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 600);
        stage.setTitle("Admin Access");
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
