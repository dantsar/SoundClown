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
    @CrossOrigin
    @GetMapping("/get/user/{user_name}")
	public ResponseEntity<?> getByUserName(@PathVariable("user_name") String user_name) {
		UserAPI userAPI = new UserAPI();
		return userAPI.get_user(user_name, dcm);
	}

	@PostMapping("/create/user")
	public ResponseEntity<?> createNewUser(@RequestBody String json) {
		UserAPI userAPI = new UserAPI();
		return userAPI.create_user(json, dcm);
	}

	@PostMapping("/update/user")
	public ResponseEntity<?> updateUser(@RequestBody String json) {
		UserAPI userAPI = new UserAPI();
		return userAPI.update_user(json, dcm);
	}

	@PostMapping("/delete/user/{user_name}")
	public ResponseEntity<?> deleteUser(@PathVariable String user_name) {
		UserAPI userAPI = new UserAPI();
		return userAPI.delete_user(user_name, dcm);
	}

	// Track
	@GetMapping("/get/track/{track_name}")
	public ResponseEntity<?> getByTrackName(@PathVariable("track_name") String track_name) {
		TrackAPI trackAPI = new TrackAPI();
		return trackAPI.get_track(track_name, dcm);
	}

	@PostMapping("/create/track")
	public ResponseEntity<?> createNewTrack(@RequestBody String json) throws JsonProcessingException {
		TrackAPI trackAPI = new TrackAPI();
		return trackAPI.create_track(json, dcm);
	}

	@PostMapping("/update/track")
	public ResponseEntity<?> updateTrack(@RequestBody String json) {
		TrackAPI trackAPI = new TrackAPI();
		return trackAPI.update_track(json, dcm);
	}

	@PostMapping("/delete/track/{track_name}")
	public ResponseEntity<?> deleteTrack(@PathVariable String track_name) {
		TrackAPI trackAPI = new TrackAPI();
		return trackAPI.delete_track(track_name, dcm);
	}

	// Playlist 
	@GetMapping("/get/playlist/{playlist_name}")
	public ResponseEntity<?> getByPlaylistName(@PathVariable("playlist_name") String track_name) {
		PlaylistAPI playlistAPI = new PlaylistAPI();
		return playlistAPI.get_playlist(track_name, dcm);
	}

	@PostMapping("/create/playlist")
	public ResponseEntity<?> createNewPlaylist(@RequestBody String json) throws JsonProcessingException {
		PlaylistAPI playlistAPI = new PlaylistAPI();
		return playlistAPI.create_playlist(json, dcm);
	}

	@PostMapping("/update/playlist")
	public ResponseEntity<?> updatePlaylist(@RequestBody String json) {
		PlaylistAPI playlistAPI = new PlaylistAPI();
		return playlistAPI.update_playlist(json, dcm);
	}

	@PostMapping("/delete/playlist/{track_name}")
	public ResponseEntity<?> deletePlaylist(@PathVariable String playlist_name) {
		PlaylistAPI playlistAPI = new PlaylistAPI();
		return playlistAPI.delete_playlist(playlist_name, dcm);
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
