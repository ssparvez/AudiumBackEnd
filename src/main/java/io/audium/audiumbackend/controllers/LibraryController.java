package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.*;
import io.audium.audiumbackend.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping(value = "/songs/{songId}")
  public Song getSong(@PathVariable long songId) {
    return libraryService.getSong(songId);
  }

  @PostMapping(value = "/songs")
  public void addSong(@RequestBody Song song) { // request body takes a json format of object and converts to java obj
    libraryService.addSong(song);
  }

  @PutMapping(value = "/songs/{songId}")
  public void updateSong(@RequestBody Song song) { // @RequestBody automatically converts JSON object to Java object
    libraryService.updateSong(song);
  }

  @DeleteMapping(value = "/songs/{songId}")
  public void removeSong(@PathVariable long songId) {
    libraryService.removeSong(songId);
  }

  // ACTUAL LIBRARY OPERATIONS
  @GetMapping(value = "/accounts/{accountId}/songs")
  public List<LibrarySong> getLibarySongs(@PathVariable long accountId) {
    return libraryService.getLibrarySongs(accountId);
  }

  @GetMapping(value = "/accounts/{accountId}/songs/recent/{pageIndex}/{pageSize}")
  public ResponseEntity<List<PopularTrack>> getCustomerSongPlays(@PathVariable long accountId, @PathVariable int pageIndex, @PathVariable int pageSize) {
    if (pageIndex < 0 || pageSize <= 0) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    List<PopularTrack> results = libraryService.getCustomerSongPlays(accountId, pageIndex, pageSize);
    if (results.size() == 0) {
      return new ResponseEntity(results, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(results, HttpStatus.OK);
  }

  @GetMapping(value = "/accounts/{accountId}/albums/recent/{pageIndex}/{pageSize}")
  public ResponseEntity<List<PopularTrack>> getCustomerAlbumSongPlays(@PathVariable long accountId, @PathVariable int pageIndex, @PathVariable int pageSize) {
    if (pageIndex < 0 || pageSize <= 0) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    List<PopularTrack> results = libraryService.getCustomerAlbumSongPlays(accountId, pageIndex, pageSize);
    if (results.size() == 0) {
      return new ResponseEntity(results, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(results, HttpStatus.OK);
  }

  @GetMapping(value = "/accounts/{accountId}/albums")
  public List<LibraryAlbum> getLibaryAlbums(@PathVariable long accountId) {
    return libraryService.getLibraryAlbums(accountId);
  }

  @GetMapping(value = "/accounts/{accountId}/artists")
  public List<LibraryArtist> getAllArtists(@PathVariable long accountId) {
    return libraryService.getLibraryArtists(accountId);
  }

  @GetMapping(value = "/accounts/{accountId}/playlists")
  public List<LibraryPlaylist> getLibaryPlaylists(@PathVariable long accountId) {
    return libraryService.getLibraryPlaylists(accountId);
  }

  @GetMapping(value = "/playlist/{playlistId}")
  public LibraryPlaylist getPlaylist(@PathVariable long playlistId) {
    return libraryService.getPlaylist(playlistId);
  }

  @GetMapping(value = "/playlist/{playlistId}/songs")
  public List<PlaylistTrack> getPlaylistSongs(@PathVariable long playlistId) {
    return libraryService.getPlaylistSongs(playlistId);
  }

  @GetMapping(value = "/album/{albumId}")
  public LibraryAlbum getAlbum(@PathVariable long albumId) {
    return libraryService.getAlbum(albumId);
  }

  @GetMapping(value = "/album/{albumId}/songs")
  public List<AlbumTrack> getAlbumSongs(@PathVariable long albumId) {
    return libraryService.getAlbumSongs(albumId);
  }

  @GetMapping(value = "/artist/{artistId}")
  public LibraryArtist getArtist(@PathVariable long artistId) {
    return libraryService.getArtist(artistId);
  }

  @GetMapping(value = "/artist/{artistId}/albums")
  public List<LibraryAlbum> getArtistAlbums(@PathVariable long artistId) {
    return libraryService.getArtistAlbums(artistId);
  }

  @GetMapping(value = "/artist/{artistId}/songs")
  public List<PopularTrack> getArtistSongs(@PathVariable long artistId) {
    return libraryService.getArtistSongs(artistId);
  }

  @GetMapping(value = "/songs/top/{pageIndex}/{pageSize}")
  @ResponseBody
  public ResponseEntity<List<PopularTrack>> getTopSongs(@PathVariable int pageIndex, @PathVariable int pageSize) {
    if (pageIndex < 0 || pageSize <= 0) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    List<PopularTrack> results = libraryService.getTopSongs(pageIndex, pageSize);
    if (results.size() == 0) {
      return new ResponseEntity(results, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(results, HttpStatus.OK);
  }
}
