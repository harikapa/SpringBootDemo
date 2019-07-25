package com.stackroute.muzixapp.service;

import com.stackroute.muzixapp.domain.Track;
import com.stackroute.muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixapp.exceptions.TrackNotFoundException;
import com.stackroute.muzixapp.repository.TrackRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@PropertySource("application.properties")
public class TrackServiceImpl implements TrackService, ApplicationListener<ContextRefreshedEvent>, CommandLineRunner {

    TrackRepository trackRepository;

    @Value("${track1.id}")
    private int id1;

    @Value("${track1.name}")
    private String name1;

    @Value("${track1.comment}")
    private String comment1;

    @Value("${track2.id}")
    private int id2;

    @Value("${track2.name}")
    private String name2;

    @Value("${track2.comment}")
    private String comment2;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository)
    {
        this.trackRepository = trackRepository;
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

    @Override
    public void run(String... args) throws Exception {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        trackRepository.save(new Track(id1,name1,comment1));
        trackRepository.save(new Track(id2,name2,comment2));
    }
}
