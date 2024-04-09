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

@SpringBootApplication
@RestController
@EnableConfigurationProperties(AudioStorageProperties.class)
public class App {

	@Autowired
    private DatabaseConnectionManager dcm;

    // User
    @GetMapping("/get/user/{user_name}")
	public User getByUserName(@PathVariable("user_name") String user_name) {
		User user = new User();
		user.set_user_name(user_name);
		try {
			Connection connection = dcm.getConnection();
			UserDAO userDAO = new UserDAO(connection);

			user = userDAO.find_by_user_name(user);
			System.out.println(user);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}


	@PostMapping("/create/user")
	public ResponseEntity<?> createNewUser(@RequestBody String json) {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			User user = new User();
			Connection connection = dcm.getConnection();
			UserDAO userDAO = new UserDAO(connection);
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

	@PostMapping("/update/user")
	public ResponseEntity<?> updateUser(@RequestBody String json) {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			User user = new User();
			Connection connection = dcm.getConnection();
			UserDAO userDAO = new UserDAO(connection);
			user.set_user_name(inputMap.get("user_name"));
			user.set_password(inputMap.get("password"));
			user.set_user_id(Integer.parseInt(inputMap.get("user_id")));
			user = userDAO.update(user);
			System.out.println(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/delete/user/{user_name}")
	public ResponseEntity<?> deleteUser(@PathVariable String user_name) {
		User user = new User();
		user.set_user_name(user_name);
		try {
			Connection connection = dcm.getConnection();
			UserDAO userDAO = new UserDAO(connection);
			userDAO.delete(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GetMapping("/get/track/{track_name}")
	public Track getByTrackName(@PathVariable("track_name") String track_name) {
		Track track = new Track();
		track.set_track_name(track_name);
		try {
			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);

			track = trackDAO.find_by_track_name(track);
			System.out.println(track);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return track;
	}

    @PostMapping("/create/track")
    public Track createNewTrack(@RequestBody String json) throws JsonProcessingException {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		Track track = new Track();
		try {
			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);

            track.set_track_name(inputMap.get("track_name"));
            track.set_description(inputMap.get("description"));
            track.set_artist_name(inputMap.get("artist_name"));
            track.set_track_path(inputMap.get("track_path"));
            track = trackDAO.create(track);

			System.out.println(track);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return track;
	}

	@PostMapping("/update/track")
	public ResponseEntity<?> updateTrack(@RequestBody String json) {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Track track = new Track();
			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);
            track.set_track_name(inputMap.get("track_name"));
            track.set_description(inputMap.get("description"));
			track.set_artist_name(inputMap.get("artist_name"));
			track.set_track_path(inputMap.get("track_path"));
			track.set_track_id(Integer.parseInt(inputMap.get("track_id")));
			track = trackDAO.update(track);
			System.out.println(track);
			return new ResponseEntity<>(track, HttpStatus.CREATED);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/delete/track/{track_name}")
	public ResponseEntity<?> deleteTrack(@PathVariable String track_name) {
		Track track = new Track();
		track.set_track_name(track_name);
		try {
			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);
			trackDAO.delete(track);
			return new ResponseEntity<>(track, HttpStatus.CREATED);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		// SpringApplication.run(UploadingFilesApplication.class, args);
	}

}
