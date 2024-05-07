package SoundClown;

import SoundClown.LikedTrack.LikedTrack;
import SoundClown.LikedTrack.LikedTrackRepository;
import SoundClown.LikedTrack.LikedTrackService;
import SoundClown.User.User;
import SoundClown.Track.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikedTrackServiceTest {

    @Mock
    private LikedTrackRepository likedTrackRepository;

    @InjectMocks
    private LikedTrackService likedTrackService;

    @Test
    public void test_create_likedtrack() {
        User user = new User();
        Track track = new Track();
        LikedTrack likedTrack = new LikedTrack(user, track);

        when(likedTrackRepository.save(Mockito.any(LikedTrack.class))).thenReturn(likedTrack);

        boolean likedTrackCreated = likedTrackService.create_likedtrack(user, track);

        Assertions.assertTrue(likedTrackCreated);
        verify(likedTrackRepository).save(Mockito.any(LikedTrack.class));
    }

    @Test
    public void test_update_likedtrack() {
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_like_id(1L);

        when(likedTrackRepository.save(Mockito.any(LikedTrack.class))).thenReturn(likedTrack);

        boolean likedTrackUpdated = likedTrackService.update_likedtrack(likedTrack);

        Assertions.assertTrue(likedTrackUpdated);
        verify(likedTrackRepository).save(Mockito.any(LikedTrack.class));
    }

    @Test
    public void test_delete_likedtrack() {
        Long likedId = 1L;

        likedTrackService.delete_likedtrack(likedId);

        verify(likedTrackRepository).deleteById(likedId);
    }
}
