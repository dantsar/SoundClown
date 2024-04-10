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

public class TrackAPI{
	public ResponseEntity<?> get_track(String track_name, DatabaseConnectionManager dcm) {
		Track track = new Track();
		track.set_track_name(track_name);
		try {
			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);

			track = trackDAO.find_by_track_name(track);
			if (track.get_track_name() != null) {
				return new ResponseEntity<>(track.to_string(), HttpStatus. CREATED);
			}
			return new ResponseEntity<>("Track " + track_name + " doesn't exist", HttpStatus.BAD_REQUEST);
		}
		catch(SQLException e) {
			return new ResponseEntity<>("Track " + track_name + " doesn't exist", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> create_track(String json, DatabaseConnectionManager dcm) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Track track = new Track();
			track.set_track_name(inputMap.get("track_name"));
			track.set_description(inputMap.get("description"));
			track.set_artist_name(inputMap.get("artist_name"));
			track.set_track_path(inputMap.get("track_path"));

			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);
			track = trackDAO.create(track);

			System.out.println(track);
			return new ResponseEntity<>(track.to_string(), HttpStatus.CREATED);

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);

		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				return new ResponseEntity<>("Duplicate track name", HttpStatus.CONFLICT);
			}
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> update_track(String json, DatabaseConnectionManager dcm) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
			Track track = new Track();
			track.set_track_name(inputMap.get("track_name"));
			track.set_description(inputMap.get("description"));
			track.set_artist_name(inputMap.get("artist_name"));
			track.set_track_path(inputMap.get("track_path"));
			track.set_track_id(Integer.parseInt(inputMap.get("track_id")));

			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);
			track = trackDAO.update(track);

			System.out.println(track);
			return new ResponseEntity<>(track.to_string(), HttpStatus.CREATED);

		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);

		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> delete_track(String track_name, DatabaseConnectionManager dcm) {
		Track track = new Track();
		track.set_track_name(track_name);
		try {
			Connection connection = dcm.getConnection();
			TrackDAO trackDAO = new TrackDAO(connection);
			trackDAO.delete(track);

			return new ResponseEntity<>("Track " + track.get_track_name() + " deleted", HttpStatus.CREATED);

		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Database error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
