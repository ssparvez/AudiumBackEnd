package io.audium.audiumbackend.controllers;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.*;
import io.audium.audiumbackend.entities.projections.*;
import io.audium.audiumbackend.services.LibraryService;
import io.audium.audiumbackend.services.VerificationService;
import io.audium.audiumbackend.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LibraryController {

  @Autowired
  private LibraryService      libraryService;
  @Autowired
  private VerificationService verificationService;

  @Autowired
  private SearchService searchService;

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
  public void updateSong(@PathVariable long songId, @RequestBody Song song) { // request body takes a json format of object and converts to java obj
    libraryService.updateSong(songId, song);
  }

  @DeleteMapping(value = "/songs/{songId}")
  public void removeSong(@PathVariable long songId) {
    libraryService.removeSong(songId);
  }

  // ACTUAL LIBRARY OPERATIONS
  @GetMapping(value = "/accounts/{accountId}/songs")
  public List<LibrarySong> getLibrarySongs(@PathVariable long accountId) {
    return libraryService.getLibrarySongs(accountId);
  }

  @GetMapping(value = "/accounts/{accountId}/albums")
  public List<LibraryAlbum> getLibraryAlbums(@PathVariable long accountId) {
    return libraryService.getLibraryAlbums(accountId);
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

  @GetMapping(value = "/accounts/{accountId}/artists")
  public List<LibraryArtist> getAllArtists(@PathVariable long accountId) {
    return libraryService.getLibraryArtists(accountId);
  }

  @PostMapping(value="/accounts/{accountId}/song/{songId}/save")
  public ResponseEntity savedSongToMusic(@RequestHeader(value = "Authorization") String token,
                                         @PathVariable long accountId,
                                         @PathVariable long songId) {
    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {
      if ( !libraryService.checkIfSongIsSaved(accountId, songId)) {
        if (libraryService.saveSongToMusic(accountId, songId)) {
          return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
      }
      else {
        return ResponseEntity.status(HttpStatus.OK).body(false);
      }

    }
    else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  @DeleteMapping(value="/accounts/{accountId}/song/{songId}/remove")
  public ResponseEntity removeSongFromMusic(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable long accountId,
                                            @PathVariable long songId) {
    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {
      if ( libraryService.removeSongFromMusic(accountId, songId) ) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);

    }
    else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  //** PLAYLIST **//

  @GetMapping(value = "/accounts/{accountId}/playlists")
  public List<LibraryPlaylist> getLibraryPlaylists(@PathVariable long accountId) {
    return libraryService.getCreatedAndFollowedPlaylists(accountId);
  }

  @GetMapping(value="/accounts/{accountId}/playlists/owned")
  public ResponseEntity getPlaylistsCreated(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable long accountId ) {
    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {
      List<LibraryPlaylist> createdPlaylists;
      if ( (createdPlaylists =libraryService.getCreatedPlaylists(accountId)) != null ) {
        return ResponseEntity.status(HttpStatus.OK).body(createdPlaylists);
      }
      else  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @GetMapping(value = "/playlist/{playlistId}/{accountId}",  produces = ("application/json"))
  public ResponseEntity getPlaylist(@PathVariable long playlistId,
                                    @PathVariable long accountId) {

    JsonObject playlistToReturn;
    if ((playlistToReturn = libraryService.getPlaylist(accountId, playlistId)) != null) {
      return ResponseEntity.status(HttpStatus.OK).body(playlistToReturn.toString());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
  }

  @GetMapping(value = "/playlist/{playlistId}/songs")
  public List<PlaylistTrack> getPlaylistSongs(@PathVariable long playlistId) {
    return libraryService.getPlaylistSongs(playlistId);
  }

  @PostMapping(value = "/playlist/newplaylist")
  public ResponseEntity createNewPlaylist(@RequestBody Playlist playlist) {
    JsonObject playlistToReturn;
    if ((playlistToReturn = libraryService.createNewPlaylist(playlist)) != null) {
      return ResponseEntity.status(HttpStatus.OK).body(playlistToReturn.toString());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
  }

  @CrossOrigin
  @DeleteMapping(value = "/playlist/delete/{accountId}/{playlistId}")
  public ResponseEntity deletePlaylist(@RequestHeader(value = "Authorization") String token,
                                       @PathVariable long accountId,
                                       @PathVariable long playlistId) {
    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {
       if (libraryService.deletePlaylist(playlistId) ) {
         return ResponseEntity.status(HttpStatus.OK).body(true);
       } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @PutMapping(value="/playlist/visibility")
  public ResponseEntity changePlaylistVisiblity(@RequestHeader(value = "Authorization") String token,
                                                @RequestBody Playlist playlist) {
    if (verificationService.verifyIntegrityCustomerAccount(token, playlist.getCreator().getAccountId()) != null) {
      if ( libraryService.changePlaylistVisibility(playlist.getPlaylistId(),playlist.getIsPublic()) ) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @PutMapping(value="/accounts/{accountId}/playlist/{playlistId}/follow/{status}")
  public ResponseEntity changePlaylistFollowStatus(@RequestHeader(value = "Authorization") String token,
                                                   @PathVariable long accountId,
                                                   @PathVariable long playlistId,
                                                   @PathVariable boolean status) {
    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {
      if (libraryService.changeFollowStatus(accountId,playlistId,status)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @PostMapping(value="/accounts/{accountId}/playlist/{playlistId}/add/song/{songId}")
  public ResponseEntity addSongToPlaylist(@RequestHeader(value = "Authorization") String token,
                                               @PathVariable long accountId,
                                               @PathVariable long playlistId,
                                               @PathVariable long songId) {
    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {

      if ( libraryService.addSongToPlaylist(playlistId, songId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }
  @CrossOrigin
  @DeleteMapping(value="/accounts/{accountId}/playlist/{playlistId}/remove/song/{songId}")
  public ResponseEntity removeSongFromPlaylist(@RequestHeader(value = "Authorization") String token,
                                               @PathVariable long accountId,
                                               @PathVariable long playlistId,
                                               @PathVariable long songId) {
    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {

      if ( libraryService.deleteSongFromPlaylist(playlistId, songId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }


  //** ALBUM **//

  @GetMapping(value = "/albums/{albumId}")
  public LibraryAlbum getAlbum(@PathVariable long albumId) {
    return libraryService.getAlbum(albumId);
  }

  @GetMapping(value = "/albums/{albumId}/songs")
  public List<AlbumTrack> getAlbumSongs(@PathVariable long albumId) {
    return libraryService.getAlbumSongs(albumId);
  }

  //** ARTIST **//

  @GetMapping(value = "/artists/{artistId}")
  public LibraryArtist getArtist(@PathVariable long artistId) {
    return libraryService.getArtist(artistId);
  }

  @GetMapping(value = "/artists/{artistId}/albums")
  public List<LibraryAlbum> getArtistAlbums(@PathVariable long artistId) {
    return libraryService.getArtistAlbums(artistId);
  }

  @GetMapping(value = "/artists/{artistId}/songs")
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

  //** EVENT **//
  @GetMapping(value = "/artist/{artistId}/events")
  public List<Event> getArtistEvents(@PathVariable long artistId) {
    return libraryService.getArtistEvents(artistId);
  }

  @GetMapping(value = "/events/{eventId}")
  public Event getEvent(@PathVariable long eventId) {
    return libraryService.getEvent(eventId);
  }

  @GetMapping(value = "/search/songs/{searchQuery}")
  public List<Song> searchSongs(@PathVariable String searchQuery) {
    return searchService.searchSongs(searchQuery);
  }

  @GetMapping(value = "/search/albums/{searchQuery}")
  public List<Album> searchAlbums(@PathVariable String searchQuery) {
    return searchService.searchAlbums(searchQuery);
  }

  @GetMapping(value = "/search/artists/{searchQuery}")
  public List<Artist> searchArtists(@PathVariable String searchQuery) {
    return searchService.searchArtists(searchQuery);
  }

  @GetMapping(value = "/search/playlists/{searchQuery}")
  public List<Playlist> searchPlaylists(@PathVariable String searchQuery) {
    return searchService.searchPlaylists(searchQuery);
  }

  @GetMapping(value = "/search/events/{searchQuery}")
  public List<Event> searchEvents(@PathVariable String searchQuery) {
    return searchService.searchEvents(searchQuery);
  }

  @GetMapping(value = "/search/profiles/{searchQuery}")
  public List<Customer> searchCustomers(@PathVariable String searchQuery) {
    return searchService.searchCustomers(searchQuery);
  }
}
