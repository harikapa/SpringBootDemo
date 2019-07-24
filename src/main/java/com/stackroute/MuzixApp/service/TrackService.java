package com.stackroute.MuzixApp.service;

import com.stackroute.MuzixApp.domain.Track;
import com.stackroute.MuzixApp.exceptions.TrackAlreadyExistsException;
import com.stackroute.MuzixApp.exceptions.TrackNotFoundException;

import java.util.List;

public interface TrackService {

    public Track saveTrack(Track track) throws TrackAlreadyExistsException;

    public List<Track> getAllTracks();

    public Track updateTrack(Track track, int id) throws TrackNotFoundException;

    public boolean deleteTrack(int id) throws TrackNotFoundException;
}
