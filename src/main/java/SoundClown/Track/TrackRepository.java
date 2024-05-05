package SoundClown.Track;

import SoundClown.Track.*;
import SoundClown.User.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("select u from #{#entityName} u where u.track_id= ?1")
    Track findTrackByTrackId(Long track_id);

    @Query("select u from #{#entityName} u where u.artist_id = ?1")
    Track findTrackByArtistId(Long track_id);

    @Query("select u from #{#entityName} u where u.track_name = ?1")
    List<Track> findTracksByTrackName(String track_name);

    @Query("select u from #{#entityName} u where u.track_name = ?1 and u.artist=?2")
    Track findTrackByTrackNameAndArtists(String track_name, User artist);

    @Query("select u from #{#entityName} u where u.artist_id = ?1")
    List<Track> findTracksByArtistId(Long artist_id);

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.artist_id = ?1")
    void deleteTracksByArtistId(Long artist_id);


    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.track_id= ?1")
    void deleteTracksByTrackId(Long track_id);
}
