package com.stackroute.muzixapp;

import com.stackroute.muzixapp.domain.Track;
import com.stackroute.muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixapp.exceptions.TrackNotFoundException;
import com.stackroute.muzixapp.service.TrackService;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@ControllerAdvice(basePackages = "com.stackroute.muzixapp")
@PropertySource("classpath:application.properties")
public class TrackController {

    TrackService trackService;

    public TrackController(TrackService trackService)
    {
        this.trackService = trackService;
    }

    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackAlreadyExistsException
    {
        Track track1 = trackService.saveTrack(track);
        return new ResponseEntity<Track>(track1, HttpStatus.CREATED);
    }

    @GetMapping("trackByName")
    public ResponseEntity<?> getTrackByName(@RequestParam String name) throws TrackNotFoundException
    {
        return new ResponseEntity<List<Track>>(trackService.getTracksByName(name), HttpStatus.OK);
    }


    @GetMapping("track")
    public ResponseEntity<?> getAllTracks() {
        ResponseEntity responseEntity;

        try {
            responseEntity = new ResponseEntity<List<Track>>(trackService.getAllTracks(), HttpStatus.OK);
        }
        catch (Exception exception) {

            responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    @PutMapping("track/{id}")
    public ResponseEntity<?> updateTrack(@RequestBody Track track, @PathVariable int id) throws TrackNotFoundException
    {
        Track track1 = trackService.updateTrack(track,id);
        return new ResponseEntity<Track>(track1, HttpStatus.OK);
    }

    @DeleteMapping("track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable int id) throws TrackNotFoundException
    {
        ResponseEntity responseEntity = null;

        if(trackService.deleteTrack(id)) {
            responseEntity = new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
        }

        return responseEntity;
    }

    @GetMapping("getLastFmTracks")
    public ResponseEntity<?> getLastFmTracks(@RequestParam String url) {

        RestTemplate restTemplate = new RestTemplate();
        String string = restTemplate.getForObject(url,String.class);
        System.out.println(string);
        Track track = restTemplate.getForObject(url,Track.class);
        return new ResponseEntity<>(track,HttpStatus.OK);
    }
}
