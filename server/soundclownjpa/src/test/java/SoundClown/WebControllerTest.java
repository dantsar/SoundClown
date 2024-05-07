package SoundClown;

import SoundClown.AudioPlayer.AudioStorageProperties;
import SoundClown.Controllers.*;
import SoundClown.LikedTrack.LikedTrackRepository;
import SoundClown.LikedTrack.LikedTrackService;
import SoundClown.User.*;
import SoundClown.Track.*;
import SoundClown.Playlist.*;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;

@WebMvcTest(value = WebController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class WebControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TrackService trackService;
    @MockBean
    private TrackRepository trackRepository;
    @MockBean
    private PlaylistService playlistService;
    @MockBean
    private PlaylistRepository playlistRepository;
    @MockBean
    private LikedTrackService likedTrackService;
    @MockBean
    private LikedTrackRepository likedTrackRepository;

    @Test
    void testRegisterUser_Success() throws Exception {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("user_name", "testUser");
        userInput.put("password", "testPassword");
        String inputJson = new ObjectMapper().writeValueAsString(userInput);

        when(userRepository.findUserByUsername("testUser")).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void testLogin_Success() throws Exception {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("user_name", "testUser");
        userInput.put("password", "testPassword");
        String inputJson = new ObjectMapper().writeValueAsString(userInput);

        User user = new User();
        user.set_user_name("testUser");
        user.set_password("testPassword");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testLogin_IncorrectCredentials() throws Exception {
        Map<String, String> userInput = new HashMap<>();
        userInput.put("user_name", "testUser");
        userInput.put("password", "wrongPassword");
        String inputJson = new ObjectMapper().writeValueAsString(userInput);

        when(userRepository.findUserByUsername("testUser")).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
        when(userService.getAllUsers()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders
                        .get("/get/allusers"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindUserById() throws Exception {
        Long userId = 1L;
        User user = new User("user", "password");
        when(userRepository.findUserByUserId(userId)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/get/user/{user_id}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void testFindUserByUsername() throws Exception {
        String username = "testUser";
        User user = new User("user", "password");
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/get/username/{username}", username))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveUser_Success() throws Exception {
        String username = "testUser";
        User user = new User("testUser", "password");
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .post("/clear/user/{user_name}", username))
                .andExpect(status().isOk()); // Expecting status code 200

    }


    @Test
    void testDeleteUser_Success() throws Exception {
        String username = "testUser";

        mvc.perform(MockMvcRequestBuilders
                        .post("/delete/user/{user_name}", username))
                .andExpect(status().isOk()); // Expecting status code 200
    }

    @Test
    void testGetAllTracks_Success() throws Exception {
        // Mock tracks
        List<Track> mockTracks = Arrays.asList(
                new Track(),
                new Track(),
                new Track()
        );

        // Mock the behavior of trackService to return mockTracks
        when(trackService.get_tracks()).thenReturn(mockTracks);

        // Perform GET request to /get/alltracks
        mvc.perform(MockMvcRequestBuilders
                        .get("/get/alltracks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUsersTracks_Success() throws Exception {
        User mockUser = new User("user", "password");

        List<Track> mockTracks = Arrays.asList(
                new Track(),
                new Track(),
                new Track()
        );

        // Mock the behavior of userRepository to return mockUser
        when(userRepository.findUserByUsername("user")).thenReturn(mockUser);

        // Mock the behavior of trackRepository to return mockTracks
        when(trackRepository.findTracksByArtistId(mockUser.get_user_id())).thenReturn(mockTracks);

        // Perform GET request to /get/user/tracks/{username}
        mvc.perform(MockMvcRequestBuilders
                        .get("/get/user/tracks/{username}", "user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindTrack_Success() throws Exception {
        Long track_id = 1L;

        // Mock track
        Track mockTrack = new Track();
        mockTrack.set_track_id(track_id);

        // Mock the behavior of trackRepository to return mockTrack
        when(trackRepository.findTrackByTrackId(track_id)).thenReturn(mockTrack);

        // Perform GET request to /get/track/{track_id}
        mvc.perform(MockMvcRequestBuilders
                        .get("/get/track/{track_id}", track_id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindTracksByTrackName_Success() throws Exception {
        String track_name = "testTrack";

        // Mock list of tracks
        List<Track> mockTracks = new ArrayList<>();
        mockTracks.add(new Track());
        mockTracks.getFirst().set_track_name(track_name);
        // Add more mock tracks as needed

        // Mock the behavior of trackRepository to return mockTracks
        when(trackRepository.findTracksByTrackName(track_name)).thenReturn(mockTracks);

        // Perform GET request to /get/tracks/track_name/{track_name}
        mvc.perform(MockMvcRequestBuilders
                        .get("/get/tracks/track_name/{track_name}", track_name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTrack_Success() throws Exception {
        // Mock input JSON payload
        String username = "testUser";
        String trackName = "testTrack";
        String trackPath = "/path/to/track";
        String description = "Test description";

        Map<String, String> userInput = new HashMap<>();
        userInput.put("username", username);
        userInput.put("track_name", trackName);
        userInput.put("track_path", trackPath);
        userInput.put("description", description);
        String inputJson = new ObjectMapper().writeValueAsString(userInput);

        // Mock the behavior of userRepository to return a user with the provided username
        User mockUser = new User(/* user details */);
        when(userRepository.findUserByUsername(username)).thenReturn(mockUser);

        // Mock the behavior of trackRepository to return null, indicating the track does not exist
        when(trackRepository.findTrackByTrackNameAndArtists(trackName, mockUser)).thenReturn(null);

        // Mock the behavior of trackService to return a track ID
        Long mockTrackId = 1L;
        when(trackService.create_track(trackName, trackPath, description, mockUser.get_user_id(), mockUser))
                .thenReturn(mockTrackId);

        // Perform POST request to /create/track endpoint
        mvc.perform(MockMvcRequestBuilders
                        .post("/create/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testPlayTrack_Success() throws Exception {
        // Mock track ID
        Long track_id = 1L;

        // Mock the behavior of trackRepository to return a mock track for the provided track ID
        Track mockTrack = new Track();
        when(trackRepository.findTrackByTrackId(track_id)).thenReturn(mockTrack);

        // Perform POST request to /play/track/{track_id} endpoint
        mvc.perform(MockMvcRequestBuilders
                        .post("/play/track/{track_id}", track_id))
                .andExpect(status().isOk());
    }

    @Test
    void testRecommendedTracks_Success() throws Exception {
        // Mock most played tracks
        List<Track> mostPlayedTracks = new ArrayList<>();
        // Add some mock tracks to the list
        mostPlayedTracks.add(new Track());
        mostPlayedTracks.add(new Track());
        mostPlayedTracks.add(new Track());

        // Mock the behavior of trackRepository to return the most played tracks
        when(trackRepository.findMostPlayedTracks()).thenReturn(mostPlayedTracks);

        // Perform GET request to /get/recommended/tracks endpoint
        mvc.perform(MockMvcRequestBuilders
                        .get("/get/recommended/tracks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePlaylist_Success() throws Exception {
        // Mock input JSON
        Map<String, String> playlistData = new HashMap<>();
        playlistData.put("user_id", "1"); // Assuming user_id exists in the database
        playlistData.put("playlist_name", "Test Playlist");
        String inputJson = new ObjectMapper().writeValueAsString(playlistData);

        // Mock the behavior of userRepository to return a user
        User user = new User(/* user details */);
        when(userRepository.findUserByUserId(1L)).thenReturn(user);

        // Mock the behavior of playlistService to return true indicating successful creation
        when(playlistService.create_playlist(user, "Test Playlist")).thenReturn(true);

        // Perform POST request to /create/playlist endpoint
        mvc.perform(MockMvcRequestBuilders
                        .post("/create/playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk());
    }

    @Test
    void testAddSongToPlaylist_Success() throws Exception {
        // Mock input JSON
        Map<String, String> inputData = new HashMap<>();
        inputData.put("playlist_id", "1"); // Assuming playlist_id exists in the database
        inputData.put("track_id", "1"); // Assuming track_id exists in the database
        String inputJson = new ObjectMapper().writeValueAsString(inputData);

        // Mock the behavior of playlistRepository to return a playlist
        Playlist playlist = new Playlist(/* playlist details */);
        when(playlistRepository.findPlaylistByPlaylistId(1L)).thenReturn(playlist);

        // Mock the behavior of trackRepository to return a track
        Track track = new Track(/* track details */);
        when(trackRepository.findTrackByTrackId(1L)).thenReturn(track);

        // Mock the behavior of playlistService to return true indicating successful update
        when(playlistService.update_playlist(playlist)).thenReturn(true);

        // Perform POST request to /addsong/playlist endpoint
        mvc.perform(MockMvcRequestBuilders
                        .post("/addsong/playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveTrackFromPlaylist_Success() throws Exception {
        // Mock input JSON
        Map<String, String> inputData = new HashMap<>();
        inputData.put("playlist_id", "1"); // Assuming playlist_id exists in the database
        inputData.put("track_id", "1"); // Assuming track_id exists in the database
        String inputJson = new ObjectMapper().writeValueAsString(inputData);

        // Mock the behavior of playlistRepository to return a playlist
        Playlist playlist = new Playlist(/* playlist details */);
        when(playlistRepository.findPlaylistByPlaylistId(1L)).thenReturn(playlist);

        // Mock the behavior of trackRepository to return a track
        Track track = new Track(/* track details */);
        when(trackRepository.findTrackByTrackId(1L)).thenReturn(track);

        // Perform POST request to /removetrack/playlist endpoint
        mvc.perform(MockMvcRequestBuilders
                        .post("/removetrack/playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk());
    }

}