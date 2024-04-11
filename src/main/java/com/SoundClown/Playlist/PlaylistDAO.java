package com.SoundClown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {
    
    private final Connection connection;

    private static final String GET_PLAYLIST    = "SELECT * FROM playlists WHERE playlist_name = ?";

    private static final String INSERT_PLAYLIST = "INSERT INTO playlists (user_id, track_id, playlist_name) VALUES (?, ?, ?)";

    private static final String UPDATE_PLAYLIST = "UPDATE playlists SET playlist_name=? WHERE playlist_id=?";

    private static final String DELETE_PLAYLIST = "DELETE FROM playlists WHERE playlist_name=?";

    public PlaylistDAO(Connection connection) { this.connection = connection; }

    public Playlist find_by_playlist_name(Playlist dto) {
        Playlist playlist = new Playlist();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_PLAYLIST)) {
            statement.setString(1, dto.get_playlist_name());
            System.out.println(dto.get_playlist_name());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                playlist.set_playlist_id(rs.getInt("playlist_id"));
                playlist.set_user_id(rs.getInt("user_id"));
                playlist.set_track_id(rs.getInt("track_id"));
                playlist.set_playlist_name(rs.getString("playlist_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return playlist;
    }


    public Playlist create(Playlist dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_PLAYLIST)) {
            statement.setInt(1, dto.get_user_id());
            statement.setInt(2, dto.get_track_id());
            statement.setString(3, dto.get_playlist_name());
            return this.find_by_playlist_name(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public Playlist update(Playlist dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_PLAYLIST)) {
            statement.setString(1, dto.get_playlist_name());
            statement.setInt(2, dto.get_playlist_id());
            statement.execute();
            return this.find_by_playlist_name(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Playlist dto) {
        System.out.println(dto.get_playlist_name());
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_PLAYLIST)) {
            statement.setString(1, dto.get_playlist_name());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

