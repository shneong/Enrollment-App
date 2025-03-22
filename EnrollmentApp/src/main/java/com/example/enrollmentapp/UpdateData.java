package com.example.enrollmentapp;

import java.sql.*;

public class UpdateData {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f3";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE students SET name=?, course=? WHERE id=?"
             )
        ) {

            String name = "Lovely Shane P. Ong";
            String course = "BSIT";
            int id = 1;

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, course);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("UPDATED SUCCESSFULLY!");
            } else {
                System.out.println("No record found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getSQLState() + " - " + e.getMessage());
        }
    }
}
