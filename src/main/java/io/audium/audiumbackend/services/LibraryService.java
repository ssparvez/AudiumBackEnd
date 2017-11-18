package io.audium.audiumbackend.services;

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

  public LibraryPlaylist getPlaylist(long playlistId) {
    return playlistRepository.findByPlaylistId(playlistId);
  }

  public LibraryAlbum getAlbum(long albumId) {
    return albumRepository.findByAlbumId(albumId);
  }

  public List<PlaylistTrack> getPlaylistSongs(long playlistId) {
    return songRepository.findPlaylistSongs(playlistId);
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

  //** PLAYLIST **//

  public Playlist createNewPlaylist(Playlist playlist) {
    try {
      playlistRepository.save(playlist);
      return playlist;
    } catch (Exception e) {
      System.out.println("EXCEPTION: " + e);
      return null;
    }
  }

  public List<LibraryPlaylist> getCreatedAndFollowedPlaylists(long accountId) {
    return playlistRepository.findCreatedAndFollowedPlaylists(accountId);
  }

  //** EVENT **//
  public Event getEvent(long eventId) {
    return eventRepository.findOne(eventId);
  }
}
