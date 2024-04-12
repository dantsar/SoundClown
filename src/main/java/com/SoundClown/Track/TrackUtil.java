package com.SoundClown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackUtil {


    private static final String TRACK_EXISTS = "SELECT COUNT(*) FROM tracks WHERE track_name = ?";

    private TrackUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean track_exists(String track_name) {
        System.out.println("CHECKING TO SEE IF TRACK EXISTS");
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();
        try (Connection connection = dcm.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(TRACK_EXISTS)) {
                System.out.println("EXECUTING QUERY");
                statement.setString(1, track_name);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    int num_tracks = rs.getInt(1);
                    System.out.println("Num Tracks = " + num_tracks);
                    if (num_tracks > 0) return true;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } catch(SQLException e) {
            return false;
        }
    }

    public static boolean track_exists(String track_name) {
        System.out.println("CHECKING TO SEE IF TRACK EXISTS");
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();
        try (Connection connection = dcm.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(TRACK_EXISTS)) {
                System.out.println("EXECUTING QUERY");
                statement.setString(1, track_name);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    int num_tracks = rs.getInt(1);
                    System.out.println("Num Tracks = " + num_tracks);
                    if (num_tracks > 0) return true;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } catch(SQLException e) {
            return false;
        }
    }
}
