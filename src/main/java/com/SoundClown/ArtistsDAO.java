package com.SoundClown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistsDAO {
    
    private final Connection connection;

    private static final String GET_ARTIST = "SELECT * FROM artists WHERE artist_name = ?";

    private static final String INSERT_ARTIST = "INSERT INTO artists (artist_name) VALUES(?)";

    private static final String UPDATE_ARTIST = "UPDATE artists SET artist_name=? " +
                                                "WHERE artist_name=?";

    private static final String DELETE_ARTIST = "DELETE FROM artists WHERE artist_name=?";

    public ArtistsDAO(Connection connection) { this.connection = connection; }

    public Artists find_by_artist_name(Artists dto) {
        Artists artist = new Artists();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ARTIST)) {
            statement.setString(1, dto.get_artist_name());
            System.out.println(dto.get_artist_name());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                artist.set_artist_id(rs.getInt("artist_id"));
                artist.set_artist_name(rs.getString("artist_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return artist;
    }

    public Artists create(Artists dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_ARTIST)) {
            statement.setString(1, dto.get_artist_name());
            statement.execute();
            return this.find_by_artist_name(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Artists update(Artists dto) {
        System.out.println(dto.get_artist_name());
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_ARTIST)) {
            statement.setString(1, dto.get_artist_name());
            statement.execute();
            return this.find_by_artist_name(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(Artists dto) {
        System.out.println(dto.get_artist_name());
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_ARTIST)) {
            statement.setString(1, dto.get_artist_name());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

