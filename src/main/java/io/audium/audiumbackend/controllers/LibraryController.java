package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping(value = "/songs")
    public List<Song> getAllSongs() {
        return libraryService.getAllSongs();
    }

    @GetMapping(value = "/songs/{id}")
    public Song getSong(@PathVariable long id) {
        return libraryService.getSong(id);
    }

    @PostMapping(value = "/songs")
    public void addSong(@RequestBody Song song) { // request body takes a json format of object and converts to java obj
        libraryService.addSong(song);
    }

    @PutMapping(value = "/songs/{id}")
    public void updateSong(@PathVariable long id, @RequestBody Song song) { // request body takes a json format of object and converts to java obj
        libraryService.updateSong(id, song);
    }

    @DeleteMapping(value = "/songs/{id}")
    public void removeSong(@PathVariable String id) {
        libraryService.removeSong(id);
    }

    @PostMapping(value = "songs/changeArtist/")
    public void changeArtist() {
        libraryService.changeArtist();
    }

    // ACTUAL LIBRARY OPERATIONS
    @GetMapping(value = "/accounts/{accountId}/songs")
    public List<Song> getLibarySongs(@PathVariable long accountId) {
        return libraryService.getLibrarySongs(accountId);
    }

    @GetMapping(value = "/accounts/{accountId}/albums")
    public List<Album> getLibaryAlbums(@PathVariable long accountId) {
        return libraryService.getLibraryAlbums(accountId);
    }

    @GetMapping(value = "/accounts/{accountId}/artists")
    public List<Artist> getAllArtists(@PathVariable long accountId) {
        return libraryService.getLibraryArtists(accountId);
    }

    @GetMapping(value = "/accounts/{accountId}/playlists")
    public List<Playlist> getLibaryPlaylists(@PathVariable long accountId) {
        return libraryService.getLibraryPlaylists(accountId);
    }

    @GetMapping(value = "/accounts/{accountId}/playlists/{playlistId}/songs")
    public List<Song> getLibaryPlaylistSongs(@PathVariable long accountId, @PathVariable long playlistId) {
        return libraryService.getLibraryPlaylistSongs(accountId, playlistId);
    }
}
