package SoundClown.Controllers;

import SoundClown.AudioPlayer.*;
import SoundClown.User.*;
import SoundClown.Track.*;
import SoundClown.Playlist.*;

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

import org.springframework.http.ResponseEntity;


import jakarta.servlet.http.HttpServletRequest;


import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@EnableConfigurationProperties(AudioStorageProperties.class)
@CrossOrigin(origins = "http://localhost:3000") // the origin of the frontend
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

	// Becuase we changed from RestController to just a controller, we need to change these interfaces
	// from JSON/XML based returns, to Views

	@GetMapping("/")
	public String first() {
		System.out.println("Going to index");
		return "registration.html";
	}

	// Register a user
	// What im implementing
	// - Verify user doesn't already exist
	@PostMapping("/register")
	public String register (@RequestParam("username") String username,
							@RequestParam("password") String password,
							Model model) {
		System.out.println(username);
		System.out.println(password);

		try {
			this.userRepository.findByUsername(username).get_user_name();
			System.out.println(username + " already exists, go to login page.");
			return "redirect:/exists";

			// We actually want this to be null (means user doesn't already exist)
		} catch (NullPointerException e) {
			// Stores version of username and password to session of controller (keeps track across requests)
			model.addAttribute("username", username);
			model.addAttribute("password", password);

			User user = new User();
			user.set_user_name(username);
			user.set_password(encoder.encode(password));
			this.userRepository.save(user);

			return "redirect:/loginPage.html";
		}
	}

	@GetMapping("/loginPage")
	public String loginPage() {
		System.out.println("Going to login page");
		return "loginPage.html";
	}

	@GetMapping("/successPage")
	public String loginSuccessPage(HttpServletRequest request) {
		String cached_username = (String) request.getSession().getAttribute("username");
		System.out.println("Going to login sucess page");
		System.out.println(cached_username);
		return "success.html";
	}

	@GetMapping("/failPage")
	public String loginFailPage() {
		System.out.println("Going to login the fail page");
		return "fail.html";
	}

	@PostMapping("/login")
	@ResponseBody
	public String login (@RequestParam("username") String username,
						 @RequestParam("password") String password,
						 Model model,
						 HttpServletRequest request) throws JsonProcessingException {

		String cached_username = (String) request.getSession().getAttribute("username");
		String cached_password = (String) request.getSession().getAttribute("password");

		if (cached_username == null) {
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			cached_username = username;
			cached_password = password;
		}

		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (authentication.isAuthenticated()) {
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			return "redirect:/successPage.html";
		} else {
			return "redirect:/failPage.html";
		}
	}

	@PostMapping("/update/password")
	@ResponseBody
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
			user.set_user_id(this.userRepository.findByUsername(username).get_user_id());
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
		return "landing.html";
	}
	@GetMapping("/get/allusers")
	@ResponseBody
	public List<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return this.userService.getAllUsers();
	}

	@GetMapping("/get/user/{user_id}")
	@ResponseBody
	public User findUser(@PathVariable("user_id") Long user_id) {
		User user = this.userRepository.findByUserId(user_id);
		return user;
	}

	@PostMapping("/delete/user/{user_id}")
	@ResponseBody
    public void deleteUser(@PathVariable("user_id") Long user_id) throws JsonProcessingException {
        System.out.println(user_id);
        this.userService.delete_user(user_id);
    }

	// Track
	@GetMapping("/get/alltracks")
	@ResponseBody
	public List<Track> getAllTracks() {
		// This returns a JSON or XML with the tracks
		List<Track> tracks = this.trackService.get_tracks();
		return tracks;
	}

	@GetMapping("/get/track/{track_id}")
	@ResponseBody
	public List<Track> findtrack(@PathVariable("track_id") Long track_id) {
		List<Track> track = trackRepository.findByTrackId(track_id);
		return track;
	}

    @PostMapping("/create/track")
	@ResponseBody
    public Long createTrack(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

		Long user_id =  Long.parseLong(inputMap.get("artist_id"));
		User user = this.userRepository.findByUserId(user_id);

		String track_name = inputMap.get("track_name");
		String track_path = inputMap.get("track_path");
		String description = inputMap.get("description");

        return this.trackService.create_track(
			track_name,
			track_path,
			description,
			user_id,
			user
        );
    }

	@PostMapping("/delete/track/{track_id}")
	@ResponseBody
    public void deleteTrack(@PathVariable("track_id") Long track_id) throws JsonProcessingException {
        System.out.println(track_id);
        this.trackService.delete_track(track_id);
    }


	// Playlist
	@GetMapping(path="/get/allplaylists")
	@ResponseBody
	public List<Playlist> getAllPlaylists() {
		// This returns a JSON or XML with the playlists
		return this.playlistService.get_playlists();
	}

	@GetMapping("/get/playlist/{playlist_name}")
	@ResponseBody
	public List<Playlist> findPlaylist(@PathVariable("playlist_name") String playlist_name) {
		return playlistRepository.findByPlaylistName(playlist_name);
	}

    @PostMapping("/create/playlist")
	@ResponseBody
    public boolean createPlaylist(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

		Long user_id =  Long.parseLong(inputMap.get("user_id"));
		User user = this.userRepository.findByUserId(user_id);

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
	@ResponseBody
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
	@ResponseBody
    public void deletePlaylist(@PathVariable("playlist_id") Long playlist_id) throws JsonProcessingException {
        System.out.println(playlist_id);
        this.playlistService.delete_playlist(playlist_id);
    }
}
