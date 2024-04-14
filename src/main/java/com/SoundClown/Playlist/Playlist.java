package SoundClown.Playlist;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name="playlists")
public class Playlist {

    @Id
    @Column(name="playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlist_id;
    @Column(name="user_id")
    private Long user_id;
    @Column(name="track_id")
    private Long track_id;
    @Column(name="playlist_name")
    private String playlist_name;

    public void set_playlist_id(Long playlist_id)       { this.playlist_id   = playlist_id;   }
    public void set_user_id(Long user_id)               { this.user_id       = user_id;       }
    public void set_track_id(Long track_id)             { this.track_id      = track_id;      }
    public void set_playlist_name(String playlist_name) { this.playlist_name = playlist_name; }

    public Long get_playlist_id()     { return playlist_id;   }
    public Long get_user_id()         { return user_id;       }
    public Long get_track_id()        { return track_id;      }
    public String get_playlist_name() { return playlist_name; }

    @Override
    public String toString() {
        return "Playlist{" +
               "playlist_id=" + playlist_id +
               ", user_id=" + user_id +
               ", track_id=" + track_id +
               ", playlist_name=" + playlist_name + '\n' +
               '}';
    }
}
