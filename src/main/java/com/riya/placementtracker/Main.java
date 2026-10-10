package com.riya.placementtracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("  PLACEMENT APPLICATION TRACKER");
        System.out.println("================================");
        System.out.println("Welcome, Riya!");
        System.out.println("Track your job applications here.");

        String url = "jdbc:mysql://localhost:3306/placement_tracker";
        String username = "root";
        String password = "Rheab30*";

        String insertSql = "INSERT INTO students (name, email, degree) VALUES (?, ?, ?)";
        String selectSql = "SELECT id, name, email, degree FROM students WHERE email = ?";

        try (Scanner scanner = new Scanner(System.in);
             Connection con = DriverManager.getConnection(url, username, password)) {

            System.out.println("MySQL Database Connected Successfully!");
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter student email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter degree: ");
            String degree = scanner.nextLine().trim();

            if (name.isEmpty() || email.isEmpty() || degree.isEmpty()) {
                System.out.println("All fields are required. No record was saved.");
                return;
            }

            try (PreparedStatement insertStatement = con.prepareStatement(insertSql)) {
                insertStatement.setString(1, name);
                insertStatement.setString(2, email);
                insertStatement.setString(3, degree);
                insertStatement.executeUpdate();
                System.out.println("Student details saved successfully!");
            }

            try (PreparedStatement selectStatement = con.prepareStatement(selectSql)) {
                selectStatement.setString(1, email);
                try (ResultSet result = selectStatement.executeQuery()) {
                    if (result.next()) {
                        System.out.println("\nSaved student record:");
                        System.out.println("ID: " + result.getInt("id"));
                        System.out.println("Name: " + result.getString("name"));
                        System.out.println("Email: " + result.getString("email"));
                        System.out.println("Degree: " + result.getString("degree"));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database operation failed: " + e.getMessage());
            System.out.println("Check your MySQL password and make sure the email is not already used.");
        }
    }
}
