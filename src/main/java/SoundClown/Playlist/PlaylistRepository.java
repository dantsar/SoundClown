package SoundClown.Playlist;

import SoundClown.User.User;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.desktop.QuitEvent;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("select u from #{#entityName} u where u.playlist_id = ?1")
    Playlist findPlaylistByPlaylistId(Long playlist_id);

    @Query("select u from #{#entityName} u where u.playlist_name = ?1")
    List<Playlist> findPlaylistByPlaylistName(String playlist_name);

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.user = ?1")
    void deletePlaylistByUser(User user);

}