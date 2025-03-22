package com.example.enrollmentapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/csit228f3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database Connected!");
        } catch (SQLException e) {
            System.err.println("Connection Failed: " + e.getMessage());
        }
    }

    public void addStudent(String name, String courseName) {
        String checkCourseQuery = "SELECT course_name FROM courses WHERE course_name = ?";
        String insertStudentQuery = "INSERT INTO students (name, course) VALUES (?, ?)";

        try (PreparedStatement checkCourseStmt = connection.prepareStatement(checkCourseQuery)) {
            checkCourseStmt.setString(1, courseName);
            ResultSet resultSet = checkCourseStmt.executeQuery();

            if (!resultSet.next()) {
                System.err.println("Error: Course '" + courseName + "' does not exist!");
                return;
            }

            try (PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentQuery)) {
                insertStudentStmt.setString(1, name);
                insertStudentStmt.setString(2, courseName);
                insertStudentStmt.executeUpdate();
                System.out.println("Student added to database!");
            }

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    public List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        String query = "SELECT name, course FROM students";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");
                students.add(" - " + name + " (" + course + ")");
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }

    public void removeStudent(String name, String courseName) {
        String query = "DELETE FROM students WHERE name = ? AND course = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, courseName);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Student removed from database!");
            } else {
                System.out.println("No matching student found.");
            }

        } catch (SQLException e) {
            System.err.println("Error removing student: " + e.getMessage());
        }
    }

    public List<String> getStudentsInCourse(String courseName) {
        List<String> students = new ArrayList<>();
        String query = "SELECT name FROM students WHERE course = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                students.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students in course: " + e.getMessage());
        }

        return students;
    }

    public List<String> getAllCourses() {
        List<String> courses = new ArrayList<>();
        String query = "SELECT course_name FROM courses";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                courses.add(resultSet.getString("course_name"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving courses: " + e.getMessage());
        }

        return courses;
    }

    public void addCourse(String courseName) {
        String query = "INSERT INTO courses (course_name) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courseName);
            statement.executeUpdate();
            System.out.println("Course added to database!");
        } catch (SQLException e) {
            System.err.println("Error adding course: " + e.getMessage());
        }
    }

    public void removeCourse(String courseName) {
        String query = "DELETE FROM courses WHERE course_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courseName);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Course removed from database!");
            } else {
                System.out.println("No matching course found.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing course: " + e.getMessage());
        }
    }

    public void updateCourse(String oldCourseName, String newCourseName) {
        String query = "UPDATE courses SET course_name = ? WHERE course_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newCourseName);
            statement.setString(2, oldCourseName);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Course updated successfully!");
            } else {
                System.out.println("Course update failed.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating course: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
