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

    // User

    @GetMapping("/user/getByUserName/{user_name}")
	public Users getByUserName(@PathVariable("user_name") String user_name) {
		System.out.println(user_name);
		DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
				"soundclown", "postgres", "password");
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
    public Users createNewUser(@RequestBody String json) throws JsonProcessingException {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
				"soundclown", "postgres", "password");
		Users user = new Users();
		try {
			Connection connection = dcm.getConnection();
			UsersDAO userDAO = new UsersDAO(connection);
            user.set_id(Integer.parseInt(inputMap.get("id")));
            user.set_user_name(inputMap.get("user_name"));
            user.set_password(inputMap.get("password"));
            user = userDAO.create(user);
			System.out.println(user);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

    @PostMapping("/tracks/createNewTrack")
    public Tracks createNewTrack(@RequestBody String json) throws JsonProcessingException {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
				"soundclown", "postgres", "password");
		Tracks track = new Tracks();
		try {
			Connection connection = dcm.getConnection();
			TracksDAO trackDAO = new TracksDAO(connection);

            track.set_id(Integer.parseInt(inputMap.get("id")));
            track.set_track_name(inputMap.get("track_name"));
            track.set_description(inputMap.get("description"));
            track.set_artist_id(Integer.parseInt(inputMap.get("artist_id")));
            track.set_genre_id(Integer.parseInt(inputMap.get("genre_id")));
            track.set_plays(Integer.parseInt(inputMap.get("plays")));
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
