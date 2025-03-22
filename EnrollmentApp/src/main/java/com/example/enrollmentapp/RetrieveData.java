package com.example.enrollmentapp;

import java.sql.*;

public class RetrieveData {
    public static final String URL = "jdbc:mysql://localhost:3306/csit228f3";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM students";
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("==== Enrolled Students ====");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String course = resultSet.getString("course");

                System.out.println(name + " is enrolled in " + course + " [ID: " + id + "]");
            }

        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getSQLState() + " - " + e.getMessage());
        }
    }
}
