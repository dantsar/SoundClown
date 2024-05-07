package SoundClown;

import SoundClown.Playlist.*;
import SoundClown.Track.Track;
import SoundClown.User.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {
    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistService playlistService;

    @Test
    public void test_get_playlists() {
        List<Playlist> mockPlaylists = Arrays.asList(
                new Playlist(),
                new Playlist()
        );

        when(playlistRepository.findAll()).thenReturn(mockPlaylists);

        List<Playlist> allPlaylists= playlistService.get_playlists();

        Assertions.assertFalse(allPlaylists.isEmpty());
    }

    @Test
    public void test_create_playlist() {
        Playlist playlist = new Playlist();
        playlist.set_playlist_id(1L);
        playlist.set_playlist_name("playlist_name");
        playlist.set_user(new User());

        when(playlistRepository.save(Mockito.any(Playlist.class))).thenReturn(playlist);

        boolean playlistSaved = playlistService.create_playlist(new User(), "playlist_name");

        Assertions.assertTrue(playlistSaved);
    }

    @Test
    public void test_update_playlists() {
        Playlist playlist = new Playlist();
        playlist.set_playlist_id(1L);
        playlist.set_playlist_name("playlist_name");
        playlist.set_user(new User());

        when(playlistRepository.save(Mockito.any(Playlist.class))).thenReturn(playlist);

        boolean playlistUpdated = playlistService.update_playlist(playlist);

        Assertions.assertTrue(playlistUpdated);
    }

}