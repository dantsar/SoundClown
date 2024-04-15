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
		return "registration";
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

			return "redirect:/loginPage";
		}
	}

	@GetMapping("/loginPage")
	public String loginPage() {
		System.out.println("Going to login page");
		return "loginPage";
	}

	@GetMapping("/successPage")
	public String loginSuccessPage(HttpServletRequest request) {
		String cached_username = (String) request.getSession().getAttribute("username");
		System.out.println("Going to login sucess page");
		System.out.println(cached_username);
		return "success";
	}

	@GetMapping("/failPage")
	public String loginFailPage() {
		System.out.println("Going to login the fail page");
		return "fail";
	}

	@PostMapping("/login")
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
			return "redirect:/successPage";
		} else {
			return "redirect:/failPage";
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
		return "landing";
	}
	@GetMapping(path="/get/allusers")
	public List<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return this.userService.getAllUsers();
	}

	@GetMapping("/get/user/{user_name}")
	public User findUser(@PathVariable("user_name") String user_name) {
		return userRepository.findByUsername(user_name);
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
		User artist = this.userRepository.findByUsername("andy");
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
