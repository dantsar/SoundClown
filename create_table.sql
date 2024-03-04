CREATE TABLE users (
    id serial,
    username varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(username, password)
);

CREATE TABLE artists ( 
    artist_id serial,
    artist_name varchar(50) UNIQUE NOT NULL,
    PRIMARY KEY(artist_id)
);

CREATE TABLE tracks (
    track_id serial,
    track_name varchar(50) UNIQUE NOT NULL,
    description varchar(500),
    artist_name varchar(50) NOT NULL,
    plays integer DEFAULT 0 NOT NULL,
    track_path varchar(500) NOT NULL,
    PRIMARY KEY (track_id),
    FOREIGN KEY (artist_name) REFERENCES artists(artist_name)
--  genre_id integer NOT NULL,
--  album_id integer,
--  art_path varchar(500),
--  FOREIGN KEY (artist_id) REFERENCES users(id) ON DELETE CASCADE,
--  FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

/*
CREATE TABLE media ( 
    user_id integer UNIQUE,
    track_id integer
);
*/

/*
CREATE TABLE playlists (
    id serial,
    playlist_name varchar(50) NOT NULL,
    description varchar(500),
    creator_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (creator_id) REFERENCES users(id)
);
*/

/*
CREATE TABLE genres (
    id serial,
    genre_name varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE albums (
    id serial,
    album_name varchar(50) NOT NULL,
    description varchar(500),
    artist_id integer NOT NULL,
    genre_id integer NOT NULL,
    art_path varchar(500),
    PRIMARY KEY (id),
    FOREIGN KEY (artist_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);
*/


/*
CREATE TABLE playlist_tracks (
    id serial,
    playlist_id integer NOT NULL,
    song_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(id),
    FOREIGN KEY (song_id) REFERENCES tracks(id)
);
*/

/*
CREATE TABLE comments (
    id serial,
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    content varchar(500),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (track_id) REFERENCES tracks(id) ON DELETE CASCADE
);
*/
