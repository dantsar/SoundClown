package SoundClown.Track;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name="tracks")
public class Track {

    @Id
    @Column(name="track_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long track_id;
    @Column(name="track_name")
    private String track_name;
    @Column(name="artist_id")
    private Long artist_id;
    @Column(name="track_path")
    private String track_path;
    @Column(name="plays")
    private int    plays;
    @Column(name="description")
    private String description;

    public void set_track_id(Long track_id)             { this.track_id      = track_id;    }
    public void set_track_name(String track_name)       { this.track_name    = track_name;  }
    public void set_artist_id(Long artist_id)           { this.artist_id     = artist_id; }
    public void set_track_path(String track_path)       { this.track_path    = track_path;  }
    public void set_plays(int plays)                    { this.plays         = plays;       }
    public void set_description(String description)     { this.description   = description; }

    public Long   get_track_id()        { return track_id;      }
    public String get_track_name()      { return track_name;    }
    public Long get_artist_id()         { return artist_id;   }
    public String get_track_path()      { return track_path;    }
    public int    get_plays()           { return plays;         }
    public String get_description()     { return description;   }

    @Override
    public String toString() {
        return "Track{" +
               "track_id=" + track_id +
               ", track_name=" + track_name +
               ", artist_id=" + artist_id +
               ", track_path=" + track_path +
               ", plays=" + plays +
               ", description=" + description + '\'' +
               '}';
    }
}
