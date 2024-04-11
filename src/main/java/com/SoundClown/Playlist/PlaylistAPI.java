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

public class PlaylistAPI {

	public ResponseEntity<?> get_playlist(String playlist_name, DatabaseConnectionManager dcm) {
		Playlist playlist = new Playlist();
		playlist.set_playlist_name(playlist_name);
		try {
			Connection connection = dcm.getConnection();
			PlaylistDAO playlistDAO = new PlaylistDAO(connection);

			playlist = playlistDAO.find_by_playlist_name(playlist);
			if (playlist.get_playlist_name() != null) {
				return new ResponseEntity<>(playlist.to_string(), HttpStatus. CREATED);
			}
			return new ResponseEntity<>("Playlist" + playlist_name + " doesn't exist", HttpStatus.BAD_REQUEST);
		}
		catch(SQLException e) {
			return new ResponseEntity<>("Playlist" + playlist_name + " doesn't exist", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> create_playlist(String json, DatabaseConnectionManager dcm) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Playlist playlist = new Playlist();
			playlist.set_user_id(Integer.parseInt(inputMap.get("user_id")));
			playlist.set_track_id(Integer.parseInt(inputMap.get("track_id")));
			playlist.set_playlist_name(inputMap.get("playlist_name"));

			Connection connection = dcm.getConnection();
			PlaylistDAO playlistDAO = new PlaylistDAO(connection);
			playlist = playlistDAO.create(playlist);

			System.out.println(playlist.to_string());
			return new ResponseEntity<>(playlist.to_string(), HttpStatus.CREATED);

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);

		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				return new ResponseEntity<>("Duplicate playlist name", HttpStatus.CONFLICT);
			}
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> update_playlist(String json, DatabaseConnectionManager dcm) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Playlist playlist = new Playlist();
			playlist.set_playlist_name(inputMap.get("playlist_name"));
			playlist.set_playlist_id(Integer.parseInt(inputMap.get("playlist_id")));

			Connection connection = dcm.getConnection();
			PlaylistDAO playlistDAO = new PlaylistDAO(connection);
			playlist = playlistDAO.update(playlist);

			System.out.println(playlist.to_string());
			return new ResponseEntity<>(playlist.to_string(), HttpStatus.CREATED);

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);

		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> delete_playlist(String playlist_name, DatabaseConnectionManager dcm) {
		Playlist playlist = new Playlist();
		playlist.set_playlist_name(playlist_name);
		try {
			Connection connection = dcm.getConnection();
			PlaylistDAO playlistDAO = new PlaylistDAO(connection);
			playlistDAO.delete(playlist);

			return new ResponseEntity<>("playlist " + playlist.get_playlist_name() + " deleted", HttpStatus.CREATED);

		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
