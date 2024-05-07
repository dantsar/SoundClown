package SoundClown.Track;

import SoundClown.User.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public List<Track> get_tracks() {
        Iterable<Track> tracks = this.trackRepository.findAll();
        List<Track> trackList = new ArrayList<>();
        tracks.forEach(track->{trackList.add(track);});
        return trackList;
    }

    public Long create_track(String track_name,
                                String track_path,
                                String image_path,
                                String description,
                                Long   artist_id,
                                User   artist) {

        Track track = new Track();
        track.set_track_name(track_name);
        track.set_artist_id(artist_id);
        track.set_description(description);
        track.set_artist(artist);
        track.set_track_path("temp"); // FIX: track_path cannot be NULL in database
        track.set_image_path("temp");
        System.out.println(track);
        Track savedTrack = this.trackRepository.save(track);
        savedTrack.set_track_path("download/track_"+savedTrack.get_track_id()+".mp3");
        savedTrack.set_image_path("download-image/image_"+savedTrack.get_track_id()+".png");
        this.trackRepository.save(savedTrack);
        System.out.println(savedTrack);
        return savedTrack.get_track_id();
    }

    public boolean update_track(Track track) {
        this.trackRepository.save(track);
        return true;
    }

    public void delete_track(Long track_id){
        this.trackRepository.deleteById(track_id);
    }
}
