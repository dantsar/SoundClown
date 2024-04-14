package SoundClown.Playlist;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("select u from #{#entityName} u where u.playlist_name = ?1")
    List<Playlist> findByPlaylistName(String playlist_name);

    @Query("select u from #{#entityName} u where u.playlist_id = ?1")
    List<Playlist> findByPlaylistId(Long playlist_id);

    @Query("select u from #{#entityName} u where u.playlist_id = ?1")
    public Playlist getPlaylistByPlaylistId(Long playlist_id);
}
