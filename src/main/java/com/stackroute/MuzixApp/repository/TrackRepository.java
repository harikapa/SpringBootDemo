package com.stackroute.MuzixApp.repository;

import com.stackroute.MuzixApp.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track,Integer> {

}
