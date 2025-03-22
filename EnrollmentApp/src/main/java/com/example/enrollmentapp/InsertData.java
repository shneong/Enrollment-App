package com.example.enrollmentapp;

import java.sql.*;

public class InsertData {
    private static final String URL = "jdbc:mysql://localhost:3306/csit228f3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        String name = "Lovely Shane P. Ong";
        String course = "BSCS";

        String insertQuery = "INSERT INTO students (name, course) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, course);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("INSERT SUCCESSFUL!");
            } else {
                System.out.println("INSERT FAILED!");
            }

        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getSQLState() + " - " + e.getMessage());
        }
    }
}
