package com.stackroute.muzixapp.service;

import com.stackroute.muzixapp.domain.Track;
import com.stackroute.muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixapp.exceptions.TrackNotFoundException;
import com.stackroute.muzixapp.repository.TrackRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TrackServiceImpl implements TrackService, InitializingBean {

    TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository)
    {
        this.trackRepository = trackRepository;
    }

    private static final Logger LOG
            = Logger.getLogger("Track service Impl entered");

    @PostConstruct
    public void init() {
        LOG.info(trackRepository.toString());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info(trackRepository.toString());
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {

        if(trackRepository.existsById(track.getId()))
        {
            throw new TrackAlreadyExistsException("Track already exists");
        }
        Track savedTrack = trackRepository.save(track);

        if(savedTrack == null)
        {
            throw new TrackAlreadyExistsException("Track already exists");
        }
        return savedTrack;
    }

    @Override
    public List<Track> getAllTracks() {

        return trackRepository.findAll();
    }

    @Override
    public List<Track> getTracksByName(String name) {

        return trackRepository.getTrackByName(name);

    }

    public Track updateTrack(Track track, int id) throws TrackNotFoundException
    {
        Optional<Track> track1 = trackRepository.findById(id);

        if(!track1.isPresent())
        {
            throw new TrackNotFoundException("Track Not Found");
        }

        track.setId(id);

        Track savedTrack = trackRepository.save(track);
        System.out.println(savedTrack.getComment());
        return savedTrack;
    }

    public boolean deleteTrack(int id) throws TrackNotFoundException
    {
        Optional<Track> track1 = trackRepository.findById(id);

        if(!track1.isPresent())
        {
            throw new TrackNotFoundException("Track Not Found");
        }

        try {

            trackRepository.delete(track1.get());

            return true;

        }
        catch (Exception exception)
        {
            return false;
        }
    }
}