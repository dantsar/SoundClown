package SoundClown;

import SoundClown.Track.*;

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
public class TrackServiceTest {
    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private TrackService trackService;

    @Test
    public void test_get_tracks() {
        List<Track> mockTracks = Arrays.asList(
                new Track(),
                new Track()
        );

        when(trackRepository.findAll()).thenReturn(mockTracks);

        List<Track> allTracks= trackService.get_tracks();

        Assertions.assertFalse(allTracks.isEmpty());
    }

    @Test
    public void test_create_track() {
        Track track = new Track();
        track.set_track_id(1L);
        track.set_track_name("track_name");
        track.set_artist_id(1L);
        track.set_description("description");
        track.set_artist(new User());
        track.set_track_path("track_path");

        when(trackRepository.save(Mockito.any(Track.class))).thenReturn(track);

        Long trackSaved = trackService.create_track("track_name",
                                                    "track_path",
                                                    "description",
                                                    1L,
                                                    new User());

        Assertions.assertNotNull(trackSaved);
    }

    @Test
    public void test_update_tracks() {
        Track track = new Track();
        track.set_track_id(1L);
        track.set_track_name("track_name");
        track.set_artist_id(1L);
        track.set_description("description");
        track.set_artist(new User());
        track.set_track_path("track_path");

        when(trackRepository.save(Mockito.any(Track.class))).thenReturn(track);

        boolean trackUpdated = trackService.update_track(track);

        Assertions.assertTrue(trackUpdated);
    }

}
