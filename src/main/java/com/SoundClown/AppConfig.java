package com.SoundClown;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// import org.springframework.stereotype.*;
// import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class AppConfig {
    @Bean
    public DatabaseConnectionManager databaseConnectionManager() {
        return new DatabaseConnectionManager("db", "soundclown", "postgres", "password");
    }

    // public Connection getConnection() throws SQLException {
    //     return DriverManager.getConnection(this.url, this.properties);
    // }
}
