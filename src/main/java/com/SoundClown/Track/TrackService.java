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

    public boolean create_track(String track_name,
                                String artist_name,
                                String track_path,
                                String description ) {

        Track track = new Track();
        track.set_track_name(track_name);
        track.set_artist_name(artist_name);
        track.set_track_path(track_path);
        track.set_description(description);
        System.out.println(track);
        this.trackRepository.save(track);
        return true;
    }
    
    public boolean update_track(Track track) {
        this.trackRepository.save(track);
        return true;
    }

    public void delete_track(Long track_id){
        this.trackRepository.deleteById(track_id);
    }

}