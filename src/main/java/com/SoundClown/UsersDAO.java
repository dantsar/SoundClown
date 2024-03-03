package com.SoundClown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    
    private final Connection connection;

    private static final String GET_USER    = "SELECT * FROM users WHERE user_name = ?";

    private static final String INSERT_USER = "INSERT INTO users (user_name, password) VALUES(?, ?)";

    public UsersDAO(Connection connection) { this.connection = connection; }

    public Users find_by_user_name(Users dto) {
        Users user = new Users();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_USER)) {
            statement.setString(1, dto.get_user_name());
            System.out.println(dto.get_user_name());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user.set_id(rs.getInt("id"));
                user.set_user_name(rs.getString("user_name"));
                user.set_password(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }

    public Users create(Users dto) {
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
}

