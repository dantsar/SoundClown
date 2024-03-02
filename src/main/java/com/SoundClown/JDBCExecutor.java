package com.SoundClown;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    // JDBC URL, username, and password of PostgreSQL server

    private static final String host     = "localhost";
    private static final String database = "clown_db";
    private static final String JDBC_URL = "jdbc:postgresql://" + host + "/" + database;
    private static final String USERNAME = "clown";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        // Create a connection to the database
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the PostgreSQL server successfully.");

            // Create a statement
            try (Statement statement = connection.createStatement()) {
                // Execute a query
                ResultSet resultSet = statement.executeQuery("SELECT version()");
                // Process the result set
                while (resultSet.next()) {
                    System.out.println("PostgreSQL database version: " + resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}