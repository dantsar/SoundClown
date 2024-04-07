package com.SoundClown;

import java.sql.Timestamp;

public class Tracks {

    private int    track_id;
    private String track_name;
    private String artist_name;
    private String track_path;
    private int    plays;
    private String description;

    public void set_track_id(int track_id)              { this.track_id      = track_id;    }
    public void set_track_name(String track_name)       { this.track_name    = track_name;  }
    public void set_artist_name(String artist_name)     { this.artist_name   = artist_name; }
    public void set_track_path(String track_path)       { this.track_path    = track_path; }
    public void set_plays(int plays)                    { this.plays         = plays;      }
    public void set_description(String description)     { this.description   = description; }

    public int    get_track_id()        { return track_id;      }
    public String get_track_name()      { return track_name;    }
    public String get_artist_name()     { return artist_name;   }
    public String get_track_path()    { return track_path;    }
    public int    get_plays()         { return plays;         }
    public String get_description()     { return description;   }

    public String to_string() {
        return "Track{" + 
               "track_id=" + track_id +
               ", track_name=" + track_name +
               ", artist_name=" + artist_name +
               ", track_path=" + track_path + 
               ", plays=" + plays + 
               ", description=" + description + '\'' + 
               '}';
    }
}