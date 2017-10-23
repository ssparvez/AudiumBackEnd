package io.audium.audiumbackend.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @RequestMapping(method = RequestMethod.GET, value = "/songs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/songs/{id}")
    public Song getSong(@PathVariable String id) {
        return songService.getSong(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/songs")
    public void addSong(@RequestBody Song song) { // request body takes a json format of object and converts to java obj
        songService.addSong(song);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/songs/{id}")
    public void updateSong(@PathVariable String id, @RequestBody Song song) { // request body takes a json format of object and converts to java obj
        songService.updateSong(id, song);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/songs/{id}")
    public void removeSong(@PathVariable String id) {
        songService.removeSong(id);
    }
}
