package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.*;
import io.audium.audiumbackend.repositories.AlbumRepository;
import io.audium.audiumbackend.repositories.ArtistRepository;
import io.audium.audiumbackend.repositories.PlaylistRepository;
import io.audium.audiumbackend.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
  
  @Autowired
  private SongRepository     songRepository;
  @Autowired
  private AlbumRepository    albumRepository;
  @Autowired
  private ArtistRepository   artistRepository;
  @Autowired
  private PlaylistRepository playlistRepository;

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

  public void updateSong(Song song) {
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

  public List<LibraryPlaylist> getLibraryPlaylists(long accountId) {
    return playlistRepository.findByFollowerAccountId(accountId);
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
}
