package com.stackroute.MuzixApp;

import com.stackroute.MuzixApp.domain.Track;
import com.stackroute.MuzixApp.exceptions.TrackNotFoundException;
import com.stackroute.MuzixApp.service.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrackController {

    TrackService trackService;

    public TrackController(TrackService trackService)
    {
        this.trackService = trackService;
    }

    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track)
    {
        ResponseEntity responseEntity;
        try
        {
            Track track1 = trackService.saveTrack(track);
            responseEntity = new ResponseEntity<String>("Inserted Successfully", HttpStatus.CREATED);
        }
        catch (Exception exception)
        {
            responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    @GetMapping("track")
    public ResponseEntity<?> getAllTracks()
    {
        return new ResponseEntity<List<Track>>(trackService.getAllTracks(),HttpStatus.OK);
    }

    @PutMapping("track/{id}")
    public ResponseEntity<?> updateTrack(@RequestBody Track track, @PathVariable int id)
    {
        ResponseEntity responseEntity;

        try
        {
            Track track1 = trackService.updateTrack(track,id);
            responseEntity = new ResponseEntity<String>("Updated Successfully", HttpStatus.OK);
        }
        catch (TrackNotFoundException exception)
        {
            responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    @DeleteMapping("track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable int id)
    {
        ResponseEntity responseEntity = null;

        try
        {
            if(trackService.deleteTrack(id)) {
                responseEntity = new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
            }
        }
        catch (TrackNotFoundException exception)
        {
            responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
        }

        return responseEntity;
    }
}
