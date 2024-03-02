package com.SoundClown;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {

    public static void main(String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "soundclown", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + " " +
                        resultSet.getString(2));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
