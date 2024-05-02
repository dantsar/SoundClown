package SoundClown.LikedTrack;

import SoundClown.Track.Track;
import SoundClown.User.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikedTrackService {

    @Autowired
    private  LikedTrackRepository likedTrackRepository;

    public boolean create_likedtrack(User user, Track track) {
        LikedTrack likedTrack = new LikedTrack();
        likedTrack.set_user(user);
        likedTrack.set_track(track);
        System.out.println(likedTrack);
        this.likedTrackRepository.save(likedTrack);
        return true;
    }

    public boolean update_likedtrack(LikedTrack likedTrack) {
        this.likedTrackRepository.save(likedTrack);
        return true;
    }

    public void delete_likedtrack(Long liked_id){
        this.likedTrackRepository.deleteById(liked_id);
    }
}