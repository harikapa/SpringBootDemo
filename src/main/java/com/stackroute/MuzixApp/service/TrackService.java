package com.stackroute.MuzixApp.service;

import com.stackroute.MuzixApp.domain.Track;

import java.util.List;

public interface TrackService {

    public Track saveTrack(Track track);

    public List<Track> getAllTracks();
}
