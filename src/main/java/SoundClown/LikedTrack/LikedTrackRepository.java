package SoundClown.LikedTrack;

import SoundClown.User.User;
import SoundClown.Track.Track;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedTrackRepository extends JpaRepository<LikedTrack, Long> {

    @Query("select  u from #{#entityName} u where u.user= ?1 and u.track = ?2")
    LikedTrack findLikedTrackByUserAndTrack(User user, Track track);

    @Query("select  u from #{#entityName} u where u.user= ?1")
    List<LikedTrack> findLikedTracksCreatedByUser(User user);

    @Query("select  u from #{#entityName} u where u.track= ?1")
    List<LikedTrack> findLikedTracksByTrack(Track track);

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.like_id= ?1")
    void deleteLikedTrackByLikeId(Long like_id);

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.user= ?1")
    void deleteLikedTracksByUser(User user);

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.track= ?1")
    void deleteLikedTrackByTrack(Track track);

    @Modifying
    @Transactional
    @Query("delete from #{#entityName} u where u.user= ?1 and u.track = ?2")
    void deleteLikedTrackByUserAndTrack(User user, Track track);

}
