package com.SoundClown;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public List get_tracks() {
        Iterable tracks = this.trackRepository.findAll();
        List trackList = new ArrayList<>();
        tracks.forEach(track->{trackList.add(track);});
        return trackList;
    }

    public Long create_track(String track_name,
                                Long artist_id,
                                String description ) {

        Track track = new Track();
        track.set_track_name(track_name);
        track.set_artist_id(artist_id);
        track.set_description(description);
        System.out.println(track);
        Track savedTrack = this.trackRepository.save(track);
        savedTrack.set_track_path("download/track_"+savedTrack.get_track_id()+".mp3");
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
