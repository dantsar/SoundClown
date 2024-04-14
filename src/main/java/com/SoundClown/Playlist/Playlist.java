package com.SoundClown.Playlist;

import com.SoundClown.Track.Track;
import com.SoundClown.User.User;
import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.List;

@Entity
@Table(name="playlists")
public class Playlist {

    @Id
    @Column(name="playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlist_id;

    @Column(name="playlist_name", length = 500)
    private String playlist_name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "playlist_track",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private List<Track> tracks;

    public void set_playlist_id(Long playlist_id)       { this.playlist_id   = playlist_id;   }
    public void set_user(User user)                     { this.user          = user;          }
    public void add_track(Track track)                  { this.tracks.add(track);             }
    public void set_playlist_name(String playlist_name) { this.playlist_name = playlist_name; }

    public Long get_playlist_id()     { return playlist_id;   }
    public User get_user()            { return user;          }
    public List<Track> get_tracks()   { return tracks;        }
    public String get_playlist_name() { return playlist_name; }

    @Override
    public String toString() {
        return "Playlist{" + 
               "playlist_id=" + playlist_id +
               ", user=" + user.toString() +
               ", tracks=" + tracks +
               ", playlist_name=" + playlist_name + '\n' +
               '}';
    }
}
