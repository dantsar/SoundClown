package SoundClown;

import SoundClown.LikedTrack.LikedTrack;
import SoundClown.User.User;
import SoundClown.Track.Track;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LikedTrackTest {

    @Test
    void set_like_id() {
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_like_id(1L);
        assertEquals(1L, likedTrack.get_like_id());
    }

    @Test
    void set_user() {
        User user = new User();
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_user(user);
        assertEquals(user, likedTrack.get_user());
    }

    @Test
    void set_track() {
        Track track = new Track();
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_track(track);
        assertEquals(track, likedTrack.get_track());
    }

    @Test
    void get_like_id() {
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_like_id(1L);
        assertEquals(1L, likedTrack.get_like_id());
    }

    @Test
    void get_user() {
        User user = new User();
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_user(user);
        assertEquals(user, likedTrack.get_user());
    }

    @Test
    void get_track() {
        Track track = new Track();
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_track(track);
        assertEquals(track, likedTrack.get_track());
    }
}