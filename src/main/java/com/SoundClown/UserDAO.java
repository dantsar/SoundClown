package com.SoundClown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    private final Connection connection;

    private static final String GET_USER    = "SELECT * FROM users WHERE user_name = ?";

    private static final String INSERT_USER = "INSERT INTO users (user_name, password) VALUES(?, ?)";

    private static final String UPDATE_USER = "UPDATE users SET user_name=?, password=? " +
                                              "WHERE user_id=?";

    private static final String DELETE_USER = "DELETE FROM users WHERE user_name=?";

    public UserDAO(Connection connection) { this.connection = connection; }

    public User find_by_user_name(User dto) {
        User user = new User();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_USER)) {
            statement.setString(1, dto.get_user_name());
            System.out.println(dto.get_user_name());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user.set_user_id(rs.getInt("user_id"));
                user.set_user_name(rs.getString("user_name"));
                user.set_password(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }

    public User create(User dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, dto.get_user_name());
            statement.setString(2, dto.get_password());
            statement.execute();
            return this.find_by_user_name(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public User update(User dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, dto.get_user_name());
            statement.setString(2, dto.get_password());
            statement.setInt(3, dto.get_user_id());
            statement.execute();
            return this.find_by_user_name(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(User dto) {
        System.out.println(dto.get_user_name());
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_USER)) {
            statement.setString(1, dto.get_user_name());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

