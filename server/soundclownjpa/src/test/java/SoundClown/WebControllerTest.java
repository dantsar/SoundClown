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
                .andExpect(status().isOk());

    }


    @Test
    void testDeleteUser_Success() throws Exception {
        String username = "testUser";

        mvc.perform(MockMvcRequestBuilders
                        .post("/delete/user/{user_name}", username))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllTracks_Success() throws Exception {
        List<Track> mockTracks = Arrays.asList(
                new Track(),
                new Track(),
                new Track()
        );

        when(trackService.get_tracks()).thenReturn(mockTracks);

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

        when(userRepository.findUserByUsername("user")).thenReturn(mockUser);

        when(trackRepository.findTracksByArtistId(mockUser.get_user_id())).thenReturn(mockTracks);

        mvc.perform(MockMvcRequestBuilders
                        .get("/get/user/tracks/{username}", "user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindTrack_Success() throws Exception {
        Long track_id = 1L;

        Track mockTrack = new Track();
        mockTrack.set_track_id(track_id);

        when(trackRepository.findTrackByTrackId(track_id)).thenReturn(mockTrack);

        mvc.perform(MockMvcRequestBuilders
                        .get("/get/track/{track_id}", track_id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindTracksByTrackName_Success() throws Exception {
        String track_name = "testTrack";

        List<Track> mockTracks = new ArrayList<>();
        mockTracks.add(new Track());
        mockTracks.getFirst().set_track_name(track_name);

        when(trackRepository.findTracksByTrackName(track_name)).thenReturn(mockTracks);

        mvc.perform(MockMvcRequestBuilders
                        .get("/get/tracks/track_name/{track_name}", track_name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTrack_Success() throws Exception {
        String username = "user";
        String track_name = "track_name";
        String track_path = "track_path";
        String description = "description";

        Map<String, String> userInput = new HashMap<>();
        userInput.put("username", username);
        userInput.put("track_name", track_name);
        userInput.put("track_path", track_path);
        userInput.put("description", description);
        String inputJson = new ObjectMapper().writeValueAsString(userInput);

        User mockUser = new User(/* user details */);
        when(userRepository.findUserByUsername(username)).thenReturn(mockUser);

        when(trackRepository.findTrackByTrackNameAndArtists(track_name, mockUser)).thenReturn(null);

        Long mockTrackId = 1L;
        when(trackService.create_track(track_name, track_path, description, mockUser.get_user_id(), mockUser))
                .thenReturn(mockTrackId);

        mvc.perform(MockMvcRequestBuilders
                        .post("/create/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testPlayTrack_Success() throws Exception {
        Long track_id = 1L;

        Track mockTrack = new Track();
        when(trackRepository.findTrackByTrackId(track_id)).thenReturn(mockTrack);

        mvc.perform(MockMvcRequestBuilders
                        .post("/play/track/{track_id}", track_id))
                .andExpect(status().isOk());
    }

    @Test
    void testRecommendedTracks_Success() throws Exception {
        List<Track> mostPlayedTracks = new ArrayList<>();
        mostPlayedTracks.add(new Track());
        mostPlayedTracks.add(new Track());
        mostPlayedTracks.add(new Track());

        when(trackRepository.findMostPlayedTracks()).thenReturn(mostPlayedTracks);

        mvc.perform(MockMvcRequestBuilders
                        .get("/get/recommended/tracks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePlaylist_Success() throws Exception {
        Map<String, String> playlistData = new HashMap<>();
        playlistData.put("user_id", "1");
        playlistData.put("playlist_name", "Test Playlist");
        String inputJson = new ObjectMapper().writeValueAsString(playlistData);

        User user = new User();
        when(userRepository.findUserByUserId(1L)).thenReturn(user);

        when(playlistService.create_playlist(user, "Test Playlist")).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/create/playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk());
    }

    @Test
    void testAddSongToPlaylist_Success() throws Exception {
        Map<String, String> inputData = new HashMap<>();
        inputData.put("playlist_id", "1");
        inputData.put("track_id", "1"); 
        String inputJson = new ObjectMapper().writeValueAsString(inputData);

        Playlist playlist = new Playlist();
        when(playlistRepository.findPlaylistByPlaylistId(1L)).thenReturn(playlist);

        Track track = new Track();
        when(trackRepository.findTrackByTrackId(1L)).thenReturn(track);

        when(playlistService.update_playlist(playlist)).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                        .post("/addsong/playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveTrackFromPlaylist_Success() throws Exception {
        Map<String, String> inputData = new HashMap<>();
        inputData.put("playlist_id", "1");
        inputData.put("track_id", "1"); 
        String inputJson = new ObjectMapper().writeValueAsString(inputData);

        Playlist playlist = new Playlist();
        when(playlistRepository.findPlaylistByPlaylistId(1L)).thenReturn(playlist);

        Track track = new Track();
        when(trackRepository.findTrackByTrackId(1L)).thenReturn(track);

        mvc.perform(MockMvcRequestBuilders
                        .post("/removetrack/playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk());
    }

}