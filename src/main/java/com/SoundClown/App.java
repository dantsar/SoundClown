package com.SoundClown;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
@RestController
public class App {

	@GetMapping("/helloClass")
	public String helloClass() {
		System.out.println("HELLO CLASS");
		return "Hello Class";
	}

	@GetMapping("/getTable")
	public String getTable() {
		DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
				"soundclown", "postgres", "password");
		try {
			Connection connection = dcm.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            String result = "";
            while(resultSet.next()){
                result += resultSet.getString(1) + " " + resultSet.getString(2);
            }
            System.out.println(result);
            return result;
		}
		catch(SQLException e) {
			e.printStackTrace();
            return "execption";
		}
	}

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}

}
