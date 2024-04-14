package com.SoundClown.Controllers;

import com.SoundClown.User.*;
import com.SoundClown.Track.*;
import com.SoundClown.Playlist.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@EnableConfigurationProperties(AudioStorageProperties.class)
public class  WebController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TrackService trackService;
	@Autowired
	private TrackRepository trackRepository;
	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private PlaylistRepository playlistRepository;

	@GetMapping(path="/get/allusers")
	public List<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return this.userService.get_users();
	}

	@GetMapping("/get/user/{user_name}")
	public List<User> findUser(@PathVariable("user_name") String user_name) {
		return userRepository.findByUserName(user_name);
	}

	@PostMapping("/create/user")
    public boolean createNewPlayer(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

        return this.userService.create_user(
                inputMap.get("user_name"),
                inputMap.get("password")
        );
    }

	@PostMapping("/update/user")
    public boolean updateUser(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		User user = new User();
		user.set_user_name(inputMap.get("user_name"));
		user.set_password(inputMap.get("password"));
		user.set_user_id(Long.parseLong(inputMap.get("user_id")));
        return this.userService.update_user(user);
    }

	@PostMapping("/delete/user/{user_id}")
    public void deleteUser(@PathVariable("user_id") Long user_id) throws JsonProcessingException {
        System.out.println(user_id);
        this.userService.delete_user(user_id);
    }

	// Track
	@GetMapping(path="/get/alltracks")
	public List<Track> getAllTracks() {
		// This returns a JSON or XML with the tracks
		return this.trackService.get_tracks();
	}

	@GetMapping("/get/track/{track_name}")
	public List<Track> findtrack(@PathVariable("track_name") String track_name) {
		return trackRepository.findByTrackName(track_name);
	}

    @PostMapping("/create/track")
    public boolean createTrack(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

        return this.trackService.create_track(
                inputMap.get("track_name"),
                inputMap.get("artist_name"),
                inputMap.get("track_path"),
                inputMap.get("description")
        );
    }

	@PostMapping("/delete/track/{track_id}")
    public void deleteTrack(@PathVariable("track_id") Long track_id) throws JsonProcessingException {
        System.out.println(track_id);
        this.trackService.delete_track(track_id);
    }


	// Playlist 
	@GetMapping(path="/get/allplaylists")
	public List<Playlist> getAllPlaylists() {
		// This returns a JSON or XML with the playlists
		return this.playlistService.get_playlists();
	}

	@GetMapping("/get/playlist/{playlist_name}")
	public List<Playlist> findPlaylist(@PathVariable("playlist_name") String playlist_name) {
		return playlistRepository.findByPlaylistName(playlist_name);
	}

    @PostMapping("/create/playlist")
    public boolean createPlaylist(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

        return this.playlistService.create_playlist(
                Long.parseLong(inputMap.get("user_id")),
                Long.parseLong(inputMap.get("track_id")),
                inputMap.get("playlist_name")
        );
    }


	@PostMapping("/delete/playlist/{playlist_id}")
    public void deletePlaylist(@PathVariable("playlist_id") Long playlist_id) throws JsonProcessingException {
        System.out.println(playlist_id);
        this.playlistService.delete_playlist(playlist_id);
    }
}
