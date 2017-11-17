package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.*;
import io.audium.audiumbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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

  public List<PopularTrack> getCustomerSongPlays(long accountId, int pageIndex, int pageSize) {
    List<PopularTrack> results    = songRepository.findCustomerSongPlays(accountId);
    int                startIndex = (pageIndex * pageSize);
    int                endIndex   = startIndex + pageSize;

    if (startIndex >= results.size()) {
      return new ArrayList<>();
    } else if (endIndex >= results.size()) {
      return results.subList(startIndex, (results.size() - startIndex));
    }
    return results.subList(startIndex, endIndex);
  }

  public List<PopularTrack> getCustomerAlbumSongPlays(long accountId, int pageIndex, int pageSize) {
    List<PopularTrack> results    = songRepository.findCustomerAlbumSongPlays(accountId);
    int                startIndex = (pageIndex * pageSize);
    int                endIndex   = startIndex + pageSize;

    if (startIndex >= results.size()) {
      return new ArrayList<>();
    } else if (endIndex >= results.size()) {
      return results.subList(startIndex, (results.size() - startIndex));
    }
    return results.subList(startIndex, endIndex);
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

  //** PLAYLIST **//

  public Playlist createNewPlaylist(Playlist playlist) {
    try {
      playlistRepository.save(playlist);
      return playlist;
    } catch (Exception e) {
      System.out.println("EXCEPTION: " + e);
      return null;
      public List<PopularTrack> getArtistSongs ( long artistId){
        return songRepository.findArtistSongs(artistId);
      }

      public List<PopularTrack> getTopSongs ( int pageIndex, int pageSize){
        List<PopularTrack> results    = songRepository.findTopSongs();
        int                startIndex = (pageIndex * pageSize);
        int                endIndex   = startIndex + pageSize;

        if (startIndex >= results.size()) {
          return new ArrayList<>();
        } else if (endIndex >= results.size()) {
          return results.subList(startIndex, (results.size() - startIndex));
        }
        return results.subList(startIndex, endIndex);
      }
    }

    public List<LibraryPlaylist> getPlaylistsFollowedAndCreated(long accountId) {
      List<LibraryPlaylist> followed = playlistRepository.findFollowedPlaylists(accountId);
      List<LibraryPlaylist> created  = playlistRepository.findCreatedPlaylists(accountId);

      if (followed != null && created != null) {
        List<LibraryPlaylist> allPlaylists = new ArrayList<>();
        Stream.of(followed, created).forEach(allPlaylists::addAll);
        allPlaylists.sort((Comparator.comparing(LibraryPlaylist::getName)));
        return allPlaylists;
      } else {
        return null;
      }
    }
}
