package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping(value = "/songs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping(value = "/songs/{id}")
    public Song getSong(@PathVariable long id) {
        return songService.getSong(id);
    }

    @PostMapping(value = "/songs")
    public void addSong(@RequestBody Song song) { // request body takes a json format of object and converts to java obj
        songService.addSong(song);
    }

    @PutMapping(value = "/songs/{id}")
    public void updateSong(@PathVariable String id, @RequestBody Song song) { // request body takes a json format of object and converts to java obj
        songService.updateSong(id, song);
    }

    @DeleteMapping(value = "/songs/{id}")
    public void removeSong(@PathVariable String id) {
        songService.removeSong(id);
    }

    @PostMapping(value = "songs/changeArtist/")
    public void changeArtist() {
        songService.changeArtist();
    }
}
