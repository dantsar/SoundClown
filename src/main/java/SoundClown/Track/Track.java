package SoundClown.Track;

import SoundClown.User.*;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name="tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="track_id")
    private Long track_id;
    @Column(name="track_name", nullable = false)
    private String track_name;
    @Column(name="artist_id", nullable = false)
    private Long  artist_id;
    @Column(name="track_path", nullable = false)
    private String track_path;
    @Column(name="image_path", nullable = false)
    private String image_path;
    @Column(name="plays", nullable = false)
    private int    plays;
    @Column(name="description", length = 500)
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id",
                referencedColumnName = "user_id",
                insertable = false,
                updatable = false)
    private User artist;

    public Track() {}

    public Track(Long track_id, String track_name, Long artist_id, String track_path, String image_path, int plays, String description, User artist) {
        this.track_id    = track_id;
        this.track_name  = track_name;
        this.artist_id   = artist_id;
        this.track_path  = track_path;
        this.image_path  = image_path;
        this.plays       = plays;
        this.description = description;
        this.artist      = artist;
    }

    public void set_track_id(Long track_id)             { this.track_id      = track_id;    }
    public void set_track_name(String track_name)       { this.track_name    = track_name;  }
    public void set_artist_id(Long artist_id)           { this.artist_id     = artist_id;   }
    public void set_track_path(String track_path)       { this.track_path    = track_path;  }
    public void set_image_path(String image_path)       { this.image_path    = image_path;  }
    public void set_plays(int plays)                    { this.plays         = plays;       }
    public void set_description(String description)     { this.description   = description; }
    public void set_artist(User artist)                 { this.artist        = artist;      }

    public Long   get_track_id()        { return track_id;      }
    public String get_track_name()      { return track_name;    }
    public Long   get_artist_id()       { return artist_id;     }
    public String get_track_path()      { return track_path;    }
    public String get_image_path()      { return image_path;    }
    public int    get_plays()           { return plays;         }
    public String get_description()     { return description;   }
    public User   get_artist()          { return artist;        }

    @Override
    public String toString() {
        return "Track{" +
               "track_id=" + track_id +
               ", track_name=" + track_name +
               ", artist_id=" + artist_id +
               ", track_path=" + track_path +
               ", image_path=" + image_path +
               ", plays=" + plays +
               ", artist=" + artist.toString() +
               ", description=" + description + '\'' +
               '}';
    }
}
