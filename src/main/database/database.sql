--how will we store images
--how will we store audio files
--(stored them as path variables)
CREATE TABLE users (
    id serial,
    user_name varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE playlists (
    id serial,
    playlist_name varchar(50) NOT NULL,
    description varchar(500),
    creator_id integer NOT NULL,
    creation_date timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
    FOREIGN KEY (creator_id) REFERENCES users(id)
);

CREATE TABLE playlist_songs (
    id serial,
    playlist_id integer NOT NULL,
    song_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(id),
    FOREIGN KEY (song_id) REFERENCES songs(id)
);

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
    creation_date timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (artist_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE tracks (
    id serial,
    track_name varchar(50) NOT NULL,
    description varchar(500),
    artist_id integer NOT NULL,
    genre_id integer NOT NULL,
    album_id integer,
    plays integer NOT NULL,
    track_path varchar(500) NOT NULL,
    art_path varchar(500),
    creation_date timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (artist_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE comments (
    id serial,
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    content varchar(500),
    creation_date timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (track_id) REFERENCES tracks(id) ON DELETE CASCADE
);
