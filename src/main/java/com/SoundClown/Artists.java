package com.SoundClown;

public class Artists {

    private int    artist_id;
    private String artist_name;

    public void set_artist_id(int artist_id)        { this.artist_id    = artist_id;   }
    public void set_artist_name(String artist_name) { this.artist_name = artist_name; }

    public int    get_artist_id()   { return artist_id;   }
    public String get_artist_name() { return artist_name; }

    public String to_string() {
        return "User{" + 
               "artist_id=" + artist_id +
               ", artist_name=" + artist_name+ '\'' +
               '}';
    }
}
