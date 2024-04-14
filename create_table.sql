CREATE TABLE users (
    user_id   serial PRIMARY KEY,
    user_name varchar(50) UNIQUE NOT NULL,
    password  varchar(50) NOT NULL,
    role      boolean NOT NULL
);

CREATE TABLE tracks (
    track_id    serial PRIMARY KEY,
    track_name  varchar(50)  NOT NULL,
    artist_id   varchar(50) NOT NULL,
    track_path  varchar(500) NOT NULL,
    plays       INT DEFAULT 0 NOT NULL,
    description varchar(500),
    FOREIGN KEY (artist_id) REFERENCES users(user_id) ON UPDATE CASCADE
);

CREATE TABLE playlists (
    playlist_name varchar(500) PRIMARY KEY,
--  playlist_id   serial PRIMARY KEY,
    user_id       serial,
    track_id      serial,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
    FOREIGN KEY (track_id) REFERENCES tracks(track_id) ON UPDATE CASCADE
);
