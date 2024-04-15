package SoundClown.Playlist;

import SoundClown.Track.Track;
import SoundClown.User.User;
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

    public boolean create_playlist(User user,
                                   String playlist_name) {

        Playlist playlist = new Playlist();
        playlist.set_user(user);
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
