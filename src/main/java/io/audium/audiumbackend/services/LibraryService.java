package io.audium.audiumbackend.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.Event;
import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.*;
import io.audium.audiumbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private SongRepository     songRepository;
  @Autowired
  private AlbumRepository    albumRepository;
  @Autowired
  private ArtistRepository   artistRepository;
  @Autowired
  private PlaylistRepository playlistRepository;
  @Autowired
  private EventRepository    eventRepository;

  public List<Song> getAllSongs() {
    List<Song> songs = new ArrayList<>();
    songRepository.findAll().forEach(songs::add);
    for (Song song : songs) {
      System.out.println(song.getTitle());
    }
    return songs;
  }

  public Song getSong(long songId) {
    return songRepository.findBySongId(songId);
  }

  public void addSong(Song song) {
    songRepository.save(song);
  }

  public void updateSong(Long songId, Song song) {
    songRepository.save(song);
  }

  public void removeSong(long songId) {
    songRepository.delete(songId);
  }

  public List<LibraryArtist> getLibraryArtists(long accountId) {
    return artistRepository.findByFollowerAccountId(accountId);
  }

  public List<LibrarySong> getLibrarySongs(long accountId) {
    return songRepository.findCustomerSongs(accountId);
  }

  public List<PopularTrack> getCustomerSongPlays(long accountId, int pageIndex, int pageSize) {
    List<PopularTrack> results = songRepository.findCustomerSongPlays(accountId);

    return HelperService.getResultsPage(pageIndex, pageSize, results);
  }

  public List<PopularTrack> getCustomerAlbumSongPlays(long accountId, int pageIndex, int pageSize) {
    List<PopularTrack> results = songRepository.findCustomerAlbumSongPlays(accountId);

    return HelperService.getResultsPage(pageIndex, pageSize, results);
  }

  public List<LibraryAlbum> getLibraryAlbums(long accountId) {
    return albumRepository.findCustomerAlbums(accountId);
  }

  public LibraryAlbum getAlbum(long albumId) {
    return albumRepository.findByAlbumId(albumId);
  }

  public List<AlbumTrack> getAlbumSongs(long albumId) {
    return songRepository.findAlbumSongs(albumId);
  }

  public LibraryArtist getArtist(long artistId) {
    return artistRepository.findByArtistId(artistId);
  }

  public List<LibraryAlbum> getArtistAlbums(long artistId) {
    return albumRepository.findArtistAlbums(artistId);
  }

  public List<PopularTrack> getArtistSongs(long artistId) {
    return songRepository.findArtistSongs(artistId);
  }

  public List<Event> getArtistEvents(long artistId) {
    return artistRepository.findArtistEvents(artistId);
  }

  public List<PopularTrack> getTopSongs(int pageIndex, int pageSize) {
    List<PopularTrack> results = songRepository.findTopSongs();

    return HelperService.getResultsPage(pageIndex, pageSize, results);
  }

  //** SONG **//

  public boolean checkIfSongIsSaved(long accountId, long songId) {
    return (songRepository.checkIfSongisSaved(accountId, songId) != null);
  }

  public boolean saveSongToMusic(long accountId, long songId) {
      return (songRepository.saveSongToMusic(accountId, songId) == 1);
  }

  public boolean removeSongFromMusic(long accountId, long songId) {
    return (songRepository.removeSongFromMusic(accountId,songId) ==1);
  }

  //** PLAYLIST **//

  public JsonObject getPlaylist(long accountId, long playlistId) {
    LibraryPlaylist playlistToReturn = playlistRepository.findByPlaylistId(playlistId);
    if (playlistToReturn != null) {
      JsonObject obj = new JsonObject();
      obj.addProperty("playlistId", playlistToReturn.getPlaylistId());
      obj.addProperty("name", playlistToReturn.getName());
      obj.addProperty("description", playlistToReturn.getDescription());
      obj.addProperty("isPublic", playlistToReturn.getIsPublic());
      obj.addProperty("accountId", playlistToReturn.getAccountId());
      obj.addProperty("username", playlistToReturn.getUsername());
      if (playlistRepository.checkIfPlaylistIsFollowed(accountId, playlistId) != null) {
        obj.addProperty("followed", true);
        return obj;
      } else {
        obj.addProperty("followed", false);
        return obj;
      }
    } else {
      return null;
    }
  }

  public List<PlaylistTrack> getPlaylistSongs(long playlistId) {
    return songRepository.findPlaylistSongs(playlistId);
  }

  private JsonObject buildPlaylistJSON(Playlist playlist) {
    JsonObject obj = new JsonObject();
    obj.addProperty("playlistId", playlist.getPlaylistId());
    obj.addProperty("name", playlist.getName());
    obj.addProperty("description", playlist.getDescription());
    obj.addProperty("isPublic", playlist.getIsPublic());
    obj.addProperty("accountId", playlist.getCreator().getAccountId());
    obj.addProperty("username", playlist.getCreator().getUsername());
    return obj;
  }

  public JsonObject createNewPlaylist(Playlist playlist) {
    try {
      playlistRepository.save(playlist);
      return buildPlaylistJSON(playlist);
    } catch (Exception e) {
      return null;
    }
  }

  public List<LibraryPlaylist> getCreatedAndFollowedPlaylists(long accountId) {
    try {
      return playlistRepository.findCreatedAndFollowedPlaylists(accountId);
    } catch (Exception e) {
      return null;
    }
  }

  public List<LibraryPlaylist> getCreatedPlaylists(long accountId) {
    try {
      return playlistRepository.findCreatedPlaylists(accountId);
    } catch ( Exception e) {
      return null;
    }
  }

  public boolean deletePlaylist(long playlistId) {

    return (playlistRepository.deletePlaylistById(playlistId) == 1);
  }

  public boolean changePlaylistVisibility(long playlistId, boolean condition) {

    Playlist playlistToSave = playlistRepository.findOne(playlistId);

    if (playlistToSave != null) {
      playlistToSave.setIsPublic(condition);
      try {
        playlistRepository.save(playlistToSave);
        return true;
      } catch (Exception e) {
        return false;
      }
    } else {
      return false;
    }
  }

  public boolean changeFollowStatus(long accountId, long playlistId, boolean status) {

    if (status) {
      return (playlistRepository.followPlaylist(playlistId, accountId) == 1);
    } else {
      return (playlistRepository.unfollowPlaylist(playlistId, accountId) == 1);
    }
  }

  public boolean deleteSongFromPlaylist(long playlistId, long songId) {

    return (playlistRepository.deleteSongFromPlaylist(playlistId, songId) == 1);
  }

  public boolean addSongToPlaylist(long playlistId, long songId) {

    return (playlistRepository.addSongToPlaylist(playlistId, songId) == 1);
  }

  //** EVENT **//
  public Event getEvent(long eventId) {
    return eventRepository.findOne(eventId);
  }
}
