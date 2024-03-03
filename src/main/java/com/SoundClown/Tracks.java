package com.SoundClown;

import java.sql.Timestamp;

public class Tracks {

    private int    id;
    private String track_name;
    private String description;
    /*
    private int    artist_id;
    private int    genre_id;
    private int    album_id;
    */
    private int    plays;
    private String track_path;
    private String art_path;

    public void set_id(int id)                          { this.id            = id;            }
    public void set_track_name(String track_name)       { this.track_name    = track_name;    }
    public void set_description(String description)     { this.description   = description;   }
    /*
    public void set_artist_id(int artist_id)            { this.artist_id     = artist_id;     }
    public void set_genre_id(int genre_id)              { this.genre_id      = genre_id;      }
    public void set_album_id(int artist_id)             { this.album_id      = album_id;      }
    */
    public void set_plays(int plays)                    { this.plays         = plays;         }
    public void set_track_path(String track_path)       { this.track_path    = track_path;    }
    public void set_art_path(String art_path)           { this.art_path      = art_path;      }

    public int    get_id()            { return id;            }
    public String get_track_name()    { return track_name;    }
    public String get_description()   { return description;   }
    /*
    public int    get_artist_id()     { return artist_id;     }
    public int    get_genre_id()      { return genre_id;      }
    public int    get_album_id()      { return album_id;      }
    */
    public int    get_plays()         { return plays;         }
    public String get_track_path()    { return track_path;    }
    public String get_art_path()      { return art_path;      }

    public String to_string() {
        return "Track{" + 
               "id=" + id +
               ", track_name=" + track_name +
               ", description=" + description + 
               /*
               ", artist_id=" + artist_id +
               ", genre_id=" + genre_id +
               ", album_id=" + album_id +
               */
               ", plays=" + plays + 
               ", track_path=" + track_path +
               ", art_path=" + art_path + '\'' +
               '}';
    }
}
