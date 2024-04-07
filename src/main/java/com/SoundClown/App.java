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

    // Users
    @GetMapping("/get/user/{user_name}")
	public ResponseEntity<?> getByUserName(@PathVariable("user_name") String user_name) {
		UsersAPI usersAPI = new UsersAPI();
		return usersAPI.get_user(user_name, dcm);
	}

	@PostMapping("/create/user")
	public ResponseEntity<?> createNewUser(@RequestBody String json) {
		UsersAPI usersAPI = new UsersAPI();
		return usersAPI.create_user(json, dcm);
	}

	@PostMapping("/update/user")
	public ResponseEntity<?> updateUser(@RequestBody String json) {
		UsersAPI usersAPI = new UsersAPI();
		return usersAPI.update_user(json, dcm);
	}

	@PostMapping("/delete/user/{user_name}")
	public ResponseEntity<?> deleteUser(@PathVariable String user_name) {
		UsersAPI usersAPI = new UsersAPI();
		return usersAPI.delete_user(user_name, dcm);
	}

	// Tracks
	@GetMapping("/get/track/{track_name}")
	public ResponseEntity<?> getByTrackName(@PathVariable("track_name") String track_name) {
		TracksAPI tracksAPI = new TracksAPI();
		return tracksAPI.get_track(track_name, dcm);
	}

	@PostMapping("/create/track")
	public ResponseEntity<?> createNewTrack(@RequestBody String json) throws JsonProcessingException {
		TracksAPI tracksAPI = new TracksAPI();
		return tracksAPI.create_track(json, dcm);
	}

	@PostMapping("/update/track")
	public ResponseEntity<?> updateTrack(@RequestBody String json) {
		TracksAPI tracksAPI = new TracksAPI();
		return tracksAPI.update_track(json, dcm);
	}

	@PostMapping("/delete/track/{track_name}")
	public ResponseEntity<?> deleteTrack(@PathVariable String track_name) {
		TracksAPI tracksAPI = new TracksAPI();
		return tracksAPI.delete_track(track_name, dcm);
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
