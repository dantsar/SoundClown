package com.SoundClown.Controllers;

import com.SoundClown.AudioStorageProperties;
import com.SoundClown.User.*;
import com.SoundClown.Track.*;
import com.SoundClown.Playlist.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;


import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@EnableConfigurationProperties(AudioStorageProperties.class)
@SessionAttributes({"username", "password"})
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
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	// Register a user
	@PostMapping("/register")
	public String register (@RequestBody String json, Model model) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

		String username = inputMap.get("user_name");
		String password = inputMap.get("password");

		System.out.println(username);
		System.out.println(password);

		model.addAttribute("username", username);
		model.addAttribute("password", password);

		User user = new User();
		user.set_user_name(username);
		user.set_password(encoder.encode(password));
		this.userRepository.save(user);

		return "landing";
	}

	@PostMapping("/login")
	public ResponseEntity login (@RequestBody String json, Model model, HttpServletRequest request) throws JsonProcessingException {
		String username = (String) request.getSession().getAttribute("username");
		String password = (String) request.getSession().getAttribute("password");

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		String input_user_name = inputMap.get("user_name");
		String input_password = inputMap.get("password");

		if (username == null) {
			model.addAttribute("username", input_user_name);
			model.addAttribute("password", input_password);
			username = input_user_name;
			password = input_password;
		}

		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (authentication.isAuthenticated()) {
			return ResponseEntity.ok().build();
		} else {
			throw new UsernameNotFoundException("invalid user request");
		}
	}

	@PostMapping("/update/password")
	public ResponseEntity createNewPlayer(@RequestBody String json, Model model, HttpServletRequest request) throws JsonProcessingException {
		String username = (String) request.getSession().getAttribute("username");
		String password = (String) request.getSession().getAttribute("password");
		System.out.println("stored " + password);

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

		String old_pw = inputMap.get("old");
		String new_pw = inputMap.get("new");

		System.out.println("old " + old_pw);
		System.out.println("new " + new_pw);


		if (old_pw.equals(password)) {
			System.out.println("Passwords matched");
			User user = new User();
			user.set_user_id(this.userRepository.findUser(username).get_user_id());
			user.set_user_name(username);
			user.set_password(encoder.encode(new_pw));
			System.out.println(user.toString());
			model.addAttribute("password",new_pw);
			this.userService.update_user(user);
			return ResponseEntity.ok().build();
		}
		System.out.println("Failed");
		return ResponseEntity.internalServerError().build();
	}

	@GetMapping("/test_postlogin")
	public String test_cookie (HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		System.out.println(username);
		return "landing";
	}
	@GetMapping(path="/get/allusers")
	public List<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return this.userService.getAllUsers();
	}

	@GetMapping("/get/user/{user_name}")
	public List<User> findUser(@PathVariable("user_name") String user_name) {
		return userRepository.findByUserName(user_name);
	}

	@GetMapping("/getUserId")
	public String readCookie(@CookieValue(name = "username", defaultValue = "default-username") String cookieName) {
		return this.userRepository.findByUserId(Long.parseLong(cookieName)).toString();
		// return String.format("value of the cookie with name username is: %s", cookieName);
	}

	/*
	@GetMapping("/create/user")
	public ResponseEntity setCookie(@RequestBody String json) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		String username = inputMap.get("user_name");
		String password = inputMap.get("password");
		System.out.println(username);
		System.out.println(password);

		User user = this.userService.create_user(username, password);

		ResponseCookie resCookie = ResponseCookie.from("username", Long.toString(user.get_user_id()))
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(24 * 60 * 60)
				.domain("localhost")
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).build();
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

	 */


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
		User artist = this.userRepository.findUser("andy");
		Long artist_id = artist.get_user_id();

        return this.trackService.create_track(
                inputMap.get("track_name"),
                inputMap.get("track_path"),
                inputMap.get("description"),
				artist_id,
				artist
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

		Long user_id =  Long.parseLong(inputMap.get("user_id"));
		User user = this.userRepository.findUserId(user_id);

		/*
		Long track_id =  Long.parseLong(inputMap.get("track_id"));
		Track track = this.trackRepository.findTrackId(track_id);
		 */

        return this.playlistService.create_playlist(
				user,
                inputMap.get("playlist_name")
        );
    }

	@PostMapping("/addsong/playlist")
	public boolean addToPlaylist(@RequestBody String json) throws JsonProcessingException {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		Long playlist_id = Long.parseLong(inputMap.get("playlist_id"));
		String track_name = inputMap.get("track_name");

		Playlist playlist = this.playlistRepository.findByPlaylistId(playlist_id);

		Track track = this.trackRepository.byTrackName(track_name);
		playlist.add_track(track);

		return this.playlistService.update_playlist(playlist);
	}

	@PostMapping("/delete/playlist/{playlist_id}")
    public void deletePlaylist(@PathVariable("playlist_id") Long playlist_id) throws JsonProcessingException {
        System.out.println(playlist_id);
        this.playlistService.delete_playlist(playlist_id);
    }
}
