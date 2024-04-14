CREATE TABLE users (
    user_id   serial      PRIMARY KEY,
    username varchar(64) UNIQUE NOT NULL,
    password  varchar(64) NOT NULL,
    role      varchar(64) NOT NULL,
);

CREATE TABLE roles (
    role_id   serial PRIMARY KEY,
    role_name varchar(64) NOT NULL,
    FOREIGN KEY (role_id) REFERENCES users(user_id) ON UPDATE CASCADE
);

CREATE TABLE tracks (
    track_id    serial        PRIMARY KEY,
    track_name  varchar(50)   NOT NULL,
    track_path  varchar(500)  NOT NULL,
    plays       INT DEFAULT 0 NOT NULL,
    description varchar(500),
    FOREIGN KEY (track_id) REFERENCES users(user_id) ON UPDATE CASCADE
);

CREATE TABLE playlists (
    playlist_id   serial PRIMARY KEY,
    user_id       serial,
    track_id      serial,
    playlist_name varchar(500),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
    FOREIGN KEY (track_id) REFERENCES tracks(track_id) ON UPDATE CASCADE
);
