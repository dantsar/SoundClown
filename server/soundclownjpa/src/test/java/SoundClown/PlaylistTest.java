package SoundClown;

import SoundClown.Playlist.Playlist;
import SoundClown.Track.Track;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class PlaylistTest {

    @Test
    void set_playlist_id() {
        Playlist playlist = new Playlist();
        playlist.set_playlist_id(1L);
        assertEquals(1L, playlist.get_playlist_id());
    }

    @Test
    void set_user() {
        Playlist playlist = new Playlist();
        User user = new User();
        playlist.set_user(user);
        assertEquals(user, playlist.get_user());
    }

    @Test
    void add_track() {
        Playlist playlist = new Playlist();
        Track track = new Track();
        playlist.add_track(track);
        List<Track> expectedTracks = new ArrayList<>();
        expectedTracks.add(track);
        assertEquals(expectedTracks, playlist.get_tracks());
    }

    @Test
    void set_playlist_name() {
        Playlist playlist = new Playlist();
        playlist.set_playlist_name("playlist_name");
        assertEquals("playlist_name", playlist.get_playlist_name());
    }

    @Test
    void get_playlist_id() {
        Playlist playlist = new Playlist();
        playlist.set_playlist_id(1L);
        assertEquals(1L, playlist.get_playlist_id());
    }
}

