package com.SoundClown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TracksDAO {

    protected final Connection connection;
    private static final String GET_TRACK    = "SELECT * FROM tracks WHERE track_name = ?";
    private static final String INSERT_TRACK = "INSERT INTO tracks (track_name, description, artist_name, track_path) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_TRACK = "UPDATE tracks " + 
                                               "SET track_name=?, " +
                                               "description=?, " +
                                               "artist_name=?, " +
                                               "track_path=? " +
                                               "WHERE track_id=?"; 

    private static final String DELETE_TRACK = "DELETE FROM tracks WHERE track_name=?";


    public TracksDAO(Connection connection) { this.connection = connection; }

    public Tracks find_by_track_name(Tracks dto) {
        Tracks track = new Tracks();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_TRACK)) {

            statement.setString(1, dto.get_track_name());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                track.set_track_id(rs.getInt("track_id"));
                track.set_track_name(rs.getString("track_name"));
                track.set_description(rs.getString("description"));
                track.set_artist_name(rs.getString("artist_name"));
                /*
                track.set_genre_id(rs.getInt("genre_id"));
                track.set_album_id(rs.getInt("ablum_id"));
                */
                track.set_plays(rs.getInt("plays"));
                track.set_track_path(rs.getString("track_path"));
                //track.set_art_path(rs.getString("art_path"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return track;
    }

    public Tracks create(Tracks dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_TRACK)) {

            statement.setString(1, dto.get_track_name());
            statement.setString(2, dto.get_description());
            statement.setString(3, dto.get_artist_name());
            /*
            statement.setInt(5, dto.get_genre_id());
            statement.setInt(6, dto.get_album_id());
            */
            statement.setString(4, dto.get_track_path());
            //statement.setString(4, dto.get_art_path());
            statement.execute();

            return this.find_by_track_name(dto);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Tracks update(Tracks dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_TRACK)) {

            statement.setString(1, dto.get_track_name());
            statement.setString(2, dto.get_description());
            statement.setString(3, dto.get_artist_name());
            /*
            statement.setInt(5, dto.get_genre_id());
            statement.setInt(6, dto.get_album_id());
            */
            statement.setString(4, dto.get_track_path());
            statement.setInt(5, dto.get_track_id());
            statement.execute();

            return this.find_by_track_name(dto);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Tracks dto) {
        System.out.println(dto.get_track_name());
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_TRACK)) {
            statement.setString(1, dto.get_track_name());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
