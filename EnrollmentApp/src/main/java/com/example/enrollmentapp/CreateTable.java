package com.example.enrollmentapp;

import java.sql.*;

public class CreateTable {
    private static final String URL = "jdbc:mysql://localhost:3306/csit228f3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            System.out.println("DATABASE CONNECTION SUCCESSFUL!");

            String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "course_name VARCHAR(100) NOT NULL UNIQUE, "
                    + "PRIMARY KEY (id))";
            statement.execute(createCoursesTable);
            System.out.println("TABLE 'courses' CREATED SUCCESSFULLY!");

            insertDefaultCourses(connection);

            String createStudentsTable = "CREATE TABLE IF NOT EXISTS students ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "course VARCHAR(100) NOT NULL, "
                    + "PRIMARY KEY (id), "
                    + "FOREIGN KEY (course) REFERENCES courses(course_name) ON DELETE CASCADE)";
            statement.execute(createStudentsTable);
            System.out.println("TABLE 'students' CREATED SUCCESSFULLY!");

        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertDefaultCourses(Connection connection) {
        String[] defaultCourses = {
                "BS Computer Science",
                "BS Information Management",
                "BS Information Systems",
                "BS Accountancy",
                "BS Nursing"
        };

        String checkQuery = "SELECT COUNT(*) FROM courses";
        String insertQuery = "INSERT INTO courses (course_name) VALUES (?)";

        try (Statement checkStatement = connection.createStatement();
             ResultSet resultSet = checkStatement.executeQuery(checkQuery)) {

            if (resultSet.next() && resultSet.getInt(1) == 0) {  // If no courses exist
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    for (String course : defaultCourses) {
                        insertStatement.setString(1, course);
                        insertStatement.executeUpdate();
                    }
                    System.out.println("DEFAULT COURSES INSERTED!");
                }
            } else {
                System.out.println("COURSES TABLE ALREADY POPULATED.");
            }

        } catch (SQLException e) {
            System.err.println("ERROR INSERTING DEFAULT COURSES: " + e.getMessage());
        }
    }
}
