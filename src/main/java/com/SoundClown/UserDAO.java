package com.SoundClown;

import com.SoundClown.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DataAccessObject<User> {
    
    private static final String GET_ONE = "SELECT id, user_name, password " + 
        "FROM user WHERE user_name=?";

    private static final String INSERT = "INSERT INTO user (id, user_name, password) " + 
        " VALUES(?, ?, ?)";

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public User find_by_user_name(User dto) {
        User user = new User();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);) {
            statement.setString(1, dto.get_user_name());
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

    @Override
    public User create(User dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);) {
            statement.setInt(1, dto.get_id());
            statement.setString(2, dto.get_user_name());
            statement.setString(3, dto.get_password());
            statement.execute();
            return this.find_by_user_name(dto);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
