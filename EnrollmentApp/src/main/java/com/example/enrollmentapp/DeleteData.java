package com.example.enrollmentapp;

import java.sql.*;

public class DeleteData {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f3";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public static void main(String[] args) {
        int id = 3; // ID to delete

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM students WHERE id=?"
             )
        ) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record with ID " + id + " deleted successfully!");
            } else {
                System.out.println("No record found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getSQLState() + " - " + e.getMessage());
        }
    }
}
