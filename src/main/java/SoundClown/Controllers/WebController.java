package SoundClown.Controllers;

import SoundClown.AudioPlayer.*;
import SoundClown.User.*;
import SoundClown.Track.*;
import SoundClown.Playlist.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Persistable;
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
@SessionAttributes("username")
//@SessionAttributes({"username", "password"})
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

	// Test to see if working

	@GetMapping("/")
	public String first() {
		System.out.println("Going to index");
		return "registration.html";
	}

	/*
	 User Functions
	 */

	@PostMapping("/register")
	@ResponseBody
	public ResponseEntity register(@RequestBody String json) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		String username = inputMap.get("user_name");
		String password = inputMap.get("password");

		System.out.println(username);
		System.out.println(password);

		try {
			this.userRepository.findUserByUsername(username).get_user_name();
			System.out.println(username + " already exists, go to login page.");
			return ResponseEntity.ok().build();

			// We actually want this to be null (means user doesn't already exist)
		} catch (NullPointerException e) {
			User user = new User();
			user.set_user_name(username);
			user.set_password(encoder.encode(password));
			this.userRepository.save(user);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity login(@RequestBody String json, Model model ) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		String username = inputMap.get("user_name");
		String password = inputMap.get("password");

		try {
			Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if (authentication.isAuthenticated()) {
				model.addAttribute("username", username);
				System.out.println("Authenticated user: " + username);
				return ResponseEntity.ok().build();
			}
		}
		// Exception means we failed
		catch (Exception e) {
			model.addAttribute("username", null);
		}
		System.out.println("Error");
		return ResponseEntity.ok().build();
	}

	@PostMapping("/update/password")
	@ResponseBody
	public ResponseEntity updatePassword(@RequestBody String json, HttpServletRequest request) throws JsonProcessingException {
		String username = (String) request.getSession().getAttribute("username");

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

		String old_pw = inputMap.get("old");
		String new_pw = inputMap.get("new");

		System.out.println("old " + old_pw);
		System.out.println("new " + new_pw);

		User assumed_user = this.userRepository.findUserByUsername(username);
		if (old_pw.equals(assumed_user.get_password())) {
			System.out.println("Passwords matched");
			assumed_user.set_user_id(assumed_user.get_user_id());
			assumed_user.set_user_name(username);
			assumed_user.set_password(encoder.encode(new_pw));
			System.out.println(assumed_user.toString());
			this.userService.update_user(assumed_user);
			return ResponseEntity.ok().build();
		}
		System.out.println("Failed");
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/whoami", produces = "text/plain")
	@ResponseBody
	public String whoami (HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		System.out.println("username: " + username);
		String ret_username = null; // for debugging (clean up later)

		if (username == null) {
			ret_username = "null";
		} else {
			ret_username = username;
		}

		System.out.println("ret_username: " + ret_username);
		return ret_username;
		// return ResponseEntity.ok().build();
	}

	@GetMapping("/get/allusers")
	@ResponseBody
	public List<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return this.userService.getAllUsers();
	}

	@GetMapping("/get/user")
	@ResponseBody
	public User getCurrentUser(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		if (username == null) {
			System.out.println("Not signed in");
			return null;
		}
		System.out.println("User is: " + username);
		return this.userRepository.findUserByUsername(username);
	}

	@GetMapping("/get/user/{user_id}")
	@ResponseBody
	public User findUser(@PathVariable("user_id") Long user_id) {
		return this.userRepository.findUserByUserId(user_id);
	}

	// There are some JPA synchronization issues that are really strange
	// Can't delete the user within the clear post request because it won't identify the foreign key constraints as
	// being resolved
	@PostMapping("/clear/user/{user_id}")
	@ResponseBody
	public ResponseEntity removeUser(@PathVariable("user_id") Long user_id) throws JsonProcessingException {
		System.out.println("Deleting playlists of user");
		this.playlistRepository.deletePlaylistByUser(this.userRepository.findUserByUserId(user_id));
		System.out.println("Deleting entries in playlists of user's tracks");
		List<Track> user_tracks = this.trackRepository.findTracksByArtistId(user_id);
		for (Track t : user_tracks) {
			this.playlistService.removeTrackFromAllPlaylists(t.get_track_id());
		}
		System.out.println("Deleting user's tracks");
		this.trackRepository.deleteTracksByArtistId(user_id);
		System.out.println("Deleting user");
		this.userRepository.deleteById(user_id);
		return ResponseEntity.ok().build();
	}

	// Needs to be called exclusively after already clearing a user
	@PostMapping("/delete/user/{user_id}")
	@ResponseBody
	public ResponseEntity deleteUser(@PathVariable("user_id") Long user_id) throws JsonProcessingException {
		this.userRepository.deleteById(user_id);
		return ResponseEntity.ok().build();
	}
	// Need to fix JPA constraint issue to resolve foreign key problem here

	/*
	 Track Functions
	 */
	@GetMapping("/get/alltracks")
	@ResponseBody
	public List<Track> getAllTracks() {
		return this.trackService.get_tracks();
	}

	@GetMapping("/get/user/tracks")
	@ResponseBody
	public List<Track> getUsersTracks(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		if (username == null) {
			System.out.println("Not signed in");
			return null;
		}
		System.out.println("User is: " + username);
		User user = this.userRepository.findUserByUsername(username);
		return this.trackRepository.findTracksByArtistId(user.get_user_id());
	}

	@GetMapping("/get/track/{track_id}")
	@ResponseBody
	public Track findtrack(@PathVariable("track_id") Long track_id) {
		return this.trackRepository.findTrackByArtistId(track_id);
	}

    @PostMapping("/create/track")
	@ResponseBody
    public Long createTrack(@RequestBody String json, HttpServletRequest request) throws JsonProcessingException {
		String username = (String) request.getSession().getAttribute("username");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

		User user = this.userRepository.findUserByUsername(username);

		String track_name = inputMap.get("track_name");
		String track_path = inputMap.get("track_path");
		String description = inputMap.get("description");

        return this.trackService.create_track(
			track_name,
			track_path,
			description,
			user.get_user_id(),
			user
        );
    }

	/*
	@PostMapping("/delete/track/{track_id}")
	@ResponseBody
    public void deleteTrack(@PathVariable("track_id") Long track_id) throws JsonProcessingException {
        this.trackService.delete_track(track_id);
    }

	 */

	@PostMapping("/delete/track/{track_id}")
	@ResponseBody
	public ResponseEntity deleteTrack(@PathVariable("track_id") Long track_id) throws JsonProcessingException {
		this.playlistService.removeTrackFromAllPlaylists(track_id);
		this.trackService.delete_track(track_id);
		return ResponseEntity.ok().build();
	}

	/*
	 Playlist Functions
	 */

	@GetMapping(path="/get/allplaylists")
	@ResponseBody
	public List<Playlist> getAllPlaylists() {
		// This returns a JSON or XML with the playlists
		return this.playlistService.get_playlists();
	}

	@GetMapping("/get/playlist/{playlist_name}")
	@ResponseBody
	public List<Playlist> findPlaylist(@PathVariable("playlist_name") String playlist_name) {
		return playlistRepository.findPlaylistByPlaylistName(playlist_name);
	}

    @PostMapping("/create/playlist")
	@ResponseBody
    public boolean createPlaylist(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);

		Long user_id =  Long.parseLong(inputMap.get("user_id"));
		User user = this.userRepository.findUserByUserId(user_id);

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

		Playlist playlist = this.playlistRepository.findPlaylistByPlaylistId(playlist_id);

		Track track = this.trackRepository.findTrackByTrackName(track_name);
		playlist.add_track(track);

		return this.playlistService.update_playlist(playlist);
	}

	// Need to fix JPA constraint issue to resolve foreign key problem here
	@PostMapping("/delete/playlist/{playlist_id}")
	@ResponseBody
    public void deletePlaylist(@PathVariable("playlist_id") Long playlist_id) throws JsonProcessingException {
        System.out.println(playlist_id);
        this.playlistService.delete_playlist(playlist_id);
    }

	@PostMapping("/delete/trackfromplaylist/")
	@ResponseBody
	public void deletePlaylist(@RequestBody String json) throws JsonProcessingException {
		System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
		this.playlistService.removeTrackFromPlaylist(Long.parseLong(inputMap.get("track_id")),
													 Long.parseLong(inputMap.get("playlist_id")));
	}
}
