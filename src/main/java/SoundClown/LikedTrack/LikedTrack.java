package SoundClown.LikedTrack;

import SoundClown.User.*;
import SoundClown.Track.*;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="liked_tracks")
public class LikedTrack {

    @Id
    @Column(name="like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "track_id")
    private Track track;

    public LikedTrack() {}

    public LikedTrack(User user, Track track) {
        this.user    = user;
        this.track   = track;
    }
    public LikedTrack(Long like_id, User user, Track track) {
        this.like_id = like_id;
        this.user    = user;
        this.track   = track;
    }

    public void set_like_id(Long like_id) { this.like_id = like_id; }
    public void set_user(User user)       { this.user    = user;    }
    public void set_track(Track track)    { this.track   = track;   }

    public Long  get_like_id() { return like_id; }
    public User  get_user()    { return user;    }
    public Track get_track()   { return track;   }

    @Override
    public String toString() {
        return "LikedTrack{" +
                "like_id=" + like_id +
                ", user=" + user.toString() +
                ", track=" + track.toString() + '\'' +
                '}';
    }
}
