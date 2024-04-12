package com.SoundClown;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List get_playlists() {
        Iterable playlists = this.playlistRepository.findAll();
        List playlistList = new ArrayList<>();
        playlists.forEach(playlist->{playlistList.add(playlist);});
        return playlistList;
    }

    public boolean create_playlist(Long user_id,
                                   Long track_id,
                                   String playlist_name) {

        Playlist playlist = new Playlist();
        playlist.set_user_id(user_id);
        playlist.set_track_id(track_id);
        playlist.set_playlist_name(playlist_name);
        System.out.println(playlist);
        this.playlistRepository.save(playlist);
        return true;
    }
    
    public boolean update_playlist(Playlist playlist) {
        this.playlistRepository.save(playlist);
        return true;
    }

    public void delete_playlist(Long playlist_id){
        this.playlistRepository.deleteById(playlist_id);
    }

}
