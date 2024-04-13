package com.SoundClown;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("select u from #{#entityName} u where u.track_name = ?1")
    List<Track> findByTrackName(String track_name);

    @Query("select u from #{#entityName} u where u.track_id = ?1")
    List<Track> findByTrackId(Long track_id);

    @Query("select u from #{#entityName} u where u.track_id = ?1")
    public Track getTrackByTrackId(Long track_id);
}
