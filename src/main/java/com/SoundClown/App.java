package com.SoundClown;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@RestController
public class App {

	@Autowired
    private DatabaseConnectionManager dcm;

	@GetMapping("/helloClass")
	public String helloClass() {
		System.out.println("HELLO CLASS");
		return "Hello Class";
	}

	@GetMapping("/getTable")
	public String getTable() {
		try {
			Connection connection = dcm.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");
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

    // User
    @GetMapping("/user/getByUserName/{user_name}")
	public Users getByUserName(@PathVariable("user_name") String user_name) {
		System.out.println(user_name);
		Users user = new Users();
		user.set_user_name(user_name);
		try {
			Connection connection = dcm.getConnection();
			UsersDAO userDAO = new UsersDAO(connection);

			user = userDAO.find_by_user_name(user);
			System.out.println(user);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}


	@PostMapping("/user/createNewUser")
	public ResponseEntity<?> createNewUser(@RequestBody String json) {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Users user = new Users();
			Connection connection = dcm.getConnection();
			UsersDAO userDAO = new UsersDAO(connection);
			user.set_user_name(inputMap.get("user_name"));
			user.set_password(inputMap.get("password"));
			user = userDAO.create(user);
			System.out.println(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
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

    @PostMapping("/tracks/createNewTrack")
    public Tracks createNewTrack(@RequestBody String json) throws JsonProcessingException {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		Tracks track = new Tracks();
		try {
			Connection connection = dcm.getConnection();
			TracksDAO trackDAO = new TracksDAO(connection);

            track.set_track_name(inputMap.get("track_name"));
            track.set_description(inputMap.get("description"));
            track.set_track_path(inputMap.get("track_path"));
            track.set_art_path(inputMap.get("art_path"));
            track = trackDAO.create(track);

			System.out.println(track);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return track;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
