package SoundClown.Playlist;

import SoundClown.Track.Track;
import SoundClown.User.User;
import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name="description", length = 500)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "playlist_track",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private List<Track> tracks;

    public Playlist() {
        this.tracks = new ArrayList<>();
    }

    public void set_playlist_id(Long playlist_id)       { this.playlist_id   = playlist_id;   }
    public void set_user(User user)                     { this.user          = user;          }
    public void add_track(Track track)                  { this.tracks.add(track);             }
    public void set_playlist_name(String playlist_name) { this.playlist_name = playlist_name; }
    public void set_description(String description)     { this.description = description;     }

    public Long get_playlist_id()     { return playlist_id;   }
    public User get_user()            { return user;          }
    public List<Track> get_tracks()   { return tracks;        }
    public String get_playlist_name() { return playlist_name; }
    public String get_description()   { return description;   }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlist_id=" + playlist_id +
                ", user=" + user.toString() +
                ", tracks=" + tracks +
                ", description=" + description +
                ", playlist_name=" + playlist_name + '\n' +
                '}';
    }
}