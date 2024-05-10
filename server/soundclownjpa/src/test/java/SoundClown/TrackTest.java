package SoundClown;

import SoundClown.Track.Track;
import SoundClown.User.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    @Test
    void set_track_id() {
        Track track = new Track();
        track.set_track_id(1L);
        assertEquals(1L, track.get_track_id());
    }

    @Test
    void set_track_name() {
        Track track = new Track();
        track.set_track_name("track_name");
        assertEquals("track_name", track.get_track_name());
    }

    @Test
    void set_artist_id() {
        Track track = new Track();
        track.set_artist_id(1L);
        assertEquals(1L, track.get_artist_id());
    }

    @Test
    void set_track_path() {
        Track track = new Track();
        track.set_track_path("track_path");
        assertEquals("track_path", track.get_track_path());
    }

    @Test
    void set_plays() {
        Track track = new Track();
        track.set_plays(0);
        assertEquals(0, track.get_plays());
    }

    @Test
    void set_description() {
        Track track = new Track();
        track.set_description("description");
        assertEquals("description", track.get_description());
    }

    @Test
    void set_artist() {
        Track track = new Track();
        User artist = new User();
        track.set_artist(artist);
        assertEquals(artist, track.get_artist());
    }

    @Test
    void testToString() {
        Track track = new Track();
        track.set_track_id(1L);
        track.set_track_name("track_name");
        track.set_artist_id(1L);
        track.set_track_path("track_path");
        track.set_plays(0);
        track.set_description("description");
        User artist = new User();
        track.set_artist(artist);

        String expectedToString = "Track{track_id=1, track_name=track_name, artist_id=1, track_path=track_path, plays=0, artist=" + artist.toString() + ", description=description'}";
        assertEquals(expectedToString, track.toString());
    }
}