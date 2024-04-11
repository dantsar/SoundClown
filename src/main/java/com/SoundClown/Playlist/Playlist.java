package com.SoundClown;

public class Playlist {

    private int    playlist_id;
    private int    user_id;
    private int    track_id;
    private String playlist_name;

    public void set_playlist_id(int playlist_id)        { this.playlist_id= playlist_id;      }
    public void set_user_id(int user_id)                { this.user_id = user_id;             }
    public void set_track_id(int track_id)              { this.track_id = track_id;           }
    public void set_playlist_name(String playlist_name) { this.playlist_name = playlist_name; }

    public int get_playlist_id()      { return playlist_id;   }
    public int get_user_id()          { return user_id;       }
    public int get_track_id()         { return track_id;      }
    public String get_playlist_name() { return playlist_name; }

    public String to_string() {
        return "Playlist{" + 
               "playlist_id=" + playlist_id +
               ", user_id=" + user_id +
               ", track_id=" + track_id + 
               ", playlist_name=" + playlist_name + '\n' +
               '}';
    }
}
