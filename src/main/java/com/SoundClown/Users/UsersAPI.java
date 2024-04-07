package com.SoundClown;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.postgresql.util.PSQLException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsersAPI{

    public ResponseEntity<?> get_user(String user_name, DatabaseConnectionManager dcm) {
        Users user = new Users();
		user.set_user_name(user_name);
		try {
			Connection connection = dcm.getConnection();
			UsersDAO userDAO = new UsersDAO(connection);

			user = userDAO.find_by_user_name(user);
			if (user.get_user_name() != null) {
				return new ResponseEntity<>(user.to_string(), HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User " + user_name + " doesn't exist", HttpStatus.BAD_REQUEST);
		}
		catch(SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("User " + user_name + " doesn't exist", HttpStatus.BAD_REQUEST);
		}
    }

	public ResponseEntity<?> create_user(String json, DatabaseConnectionManager dcm) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Users user = new Users();
			user.set_user_name(inputMap.get("user_name"));
			user.set_password(inputMap.get("password"));

			Connection connection = dcm.getConnection();
			UsersDAO userDAO = new UsersDAO(connection);
			user = userDAO.create(user);

			System.out.println(user);
			return new ResponseEntity<>(user.to_string(), HttpStatus.CREATED);

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);

		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				return new ResponseEntity<>("Duplicate user name", HttpStatus.CONFLICT);
			}
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> update_user(String json, DatabaseConnectionManager dcm) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Users user = new Users();
			user.set_user_name(inputMap.get("user_name"));
			user.set_password(inputMap.get("password"));
			user.set_user_id(Integer.parseInt(inputMap.get("user_id")));

			Connection connection = dcm.getConnection();
			UsersDAO userDAO = new UsersDAO(connection);
			user = userDAO.update(user);

			System.out.println(user);
			return new ResponseEntity<>(user.to_string(), HttpStatus.CREATED);

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);

		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> delete_user(String user_name, DatabaseConnectionManager dcm) {
		Users user = new Users();
		user.set_user_name(user_name);
		try {
			Connection connection = dcm.getConnection();
			UsersDAO userDAO = new UsersDAO(connection);
			userDAO.delete(user);
			return new ResponseEntity<>("User " + user.get_user_name() + " deleted", HttpStatus.CREATED);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}