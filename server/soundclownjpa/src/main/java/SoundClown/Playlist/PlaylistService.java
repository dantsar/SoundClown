package SoundClown.Playlist;


import SoundClown.User.User;
import SoundClown.Track.Track;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> get_playlists() {
        Iterable<Playlist> playlists = this.playlistRepository.findAll();
        List<Playlist> playlistList = new ArrayList<>();
        playlists.forEach(playlist->{playlistList.add(playlist);});
        return playlistList;
    }

    public boolean create_playlist(User user,
                                   String playlist_name,
                                   String description) {

        Playlist playlist = new Playlist();
        playlist.set_user(user);
        playlist.set_playlist_name(playlist_name);
        playlist.set_description(description);
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

    public void removeTrackFromPlaylist(Long playlistId, Long trackIdToRemove) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist != null) {
            Track trackToRemove = playlist.get_tracks().stream()
                    .filter(track -> track.get_track_id().equals(trackIdToRemove))
                    .findFirst().orElse(null);
            if (trackToRemove != null) {
                playlist.get_tracks().remove(trackToRemove);
                playlistRepository.save(playlist);
            }
        }
    }

    public void removeAllTracksFromPlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist != null) {
            List<Track> tracksToRemove = playlist.get_tracks();
            for (int i = 0; i < tracksToRemove.size(); i++) {
                playlist.get_tracks().remove(tracksToRemove.get(i));
                playlistRepository.save(playlist);
            }
        }
    }

    public void removeTrackFromAllPlaylists(Long trackIdToRemove) {
        List<Playlist> allPlaylists = playlistRepository.findAll();
        for (Playlist p : allPlaylists) {
            List<Track> playlist_tracks = p.get_tracks();
            for (int i = 0; i < playlist_tracks.size(); i++) {
                if (playlist_tracks.get(i).get_track_id().equals(trackIdToRemove)) {
                    playlist_tracks.remove(playlist_tracks.get(i));
                    playlistRepository.save(p);
                }
            }
        }
    }
}
