package io.audium.audiumbackend.services;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.*;
import io.audium.audiumbackend.entities.projections.*;
import io.audium.audiumbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
  // Number of top songs to retrieve from the database when browsing a genre page
  public static long GENRE_TOP_SONG_COUNT = 50;

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
  @Autowired
  private GenreRepository    genreRepository;
  @Autowired
  private LabelRepository    labelRepository;

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

  public List<LibraryArtist> getLibraryArtists(long accountId) {
    return artistRepository.findByFollowerAccountId(accountId);
  }

  public List<LibrarySong> getLibrarySongs(long accountId) {
    return songRepository.findCustomerSongs(accountId);
  }

  public List<RecentTrack> getPublicSongPlays(long accountId) {
    return songRepository.findPublicSongPlays(accountId);
  }

  public List<PopularTrack> getCustomerSongPlays(long accountId, int pageIndex, int pageSize) {
    List<PopularTrack> results = songRepository.findCustomerSongPlays(accountId);

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

  public LibraryArtist getArtistByAccountId(long accountId) {
    return artistRepository.findByAccountId(accountId);
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

  //** GENRE **//

  public Iterable<Genre> getAllGenres() {
    return genreRepository.findAll();
  }

  public Genre getGenreSongsAndAlbums(long genreId) {
    Genre genre = genreRepository.findOne(genreId);
    if (genre == null) {
      return null;
    }
    // NOTE: In songQuery, playCountLastMonth is actually playCount; just using playCountLastMonth to circumvent JPA
    String songQuery =
      "   SELECT S.songId AS songId, S.title AS title, Art.artistId AS artistId, Art.name AS artistName, Alb.albumId AS albumId, Alb.title AS albumTitle, S.file AS file, S.duration AS duration, S.isExplicit AS isExplicit, S.year AS year, S.genreId AS genreId, \"" + genre.getGenreName() + "\" AS genreName, S.playCountLastMonth AS playCountLastMonth "
        + " FROM (SELECT GS.*, COUNT(GS.songId) AS playCountLastMonth FROM Song GS, song_play SP WHERE GS.songId = SP.songId AND GS.genreId = " + genreId + " GROUP BY GS.songId LIMIT " + LibraryService.GENRE_TOP_SONG_COUNT + ") AS S, "
        + " artist_song ArtS, Artist Art, album_song AlbS, Album Alb WHERE S.songId = ArtS.songId AND S.songId = AlbS.songId AND Art.artistId = ArtS.artistId AND Alb.albumId = AlbS.albumId GROUP BY S.songId ORDER BY S.playCountLastMonth DESC, S.year DESC, S.title ASC";

    String artistQuery = "SELECT A.artistId AS artistId, A.name AS artistName, A.bio AS bio FROM Artist A, artist_genre AG WHERE A.artistId = AG.artistId AND AG.genreId = " + genreId + " ORDER BY A.name ASC";
    genre.setSongs(songRepository.findSongsByGenreId(songQuery));
    genre.setArtists(artistRepository.getArtistsByCustomQuery(artistQuery));
    return genre;
  }

  // LABEL **/

  public Iterable<Object> getAllLabels() {
    return labelRepository.getAllLabels();
  }

  //** ALBUM **//

  public boolean changeAlbumSavedStatus(long accountId, long albumId, boolean status) {
    if (status) {
      return (albumRepository.saveAlbum(accountId, albumId) == 1);
    } else {
      return (albumRepository.removeAlbum(albumId, accountId) == 1);
    }
  }

  public List<Long> getListOfSavedAlbumIds(long accountId) {
    List<Long> albumsSaved = albumRepository.getListOfSavedAlbumIds(accountId);
    if (albumsSaved != null) {
      return albumsSaved;
    } else {
      return null;
    }
  }

  public boolean addSongToAlbum(long albumId, long songId) {

    return (albumRepository.addSongToAlbum(albumId, songId) == 1);
  }

  public boolean verifyAlbumExists(long albumId) {
    return albumRepository.exists(albumId);
  }

  //** ARTIST **//

  public boolean changeArtistFollowStatus(long accountId, long artistId, boolean status) {
    if (status) {
      return (artistRepository.followArtist(artistId, accountId) == 1);
    } else {
      return (artistRepository.unfollowArtist(artistId, accountId) == 1);
    }
  }
  public List<Long> getListOfFollowedArtistIds(long accountId) {
    List<Long> artistsFollowed = artistRepository.getListOfFollowedArtistIds(accountId);

    if (artistsFollowed != null) {
      return artistsFollowed;
    } else {
      return null;
    }
  }

  public List<LibraryArtist> getSimilarArtists(long artistId) {
    String query =
      "     SELECT Sim.artistId AS artistId, Sim.name AS artistName, Sim.bio AS bio FROM ( "
        + "    SELECT A.*, SUM(IF(AG.primaryGenre = TRUE, 2, 1)) AS Similarity FROM Artist A, artist_genre AG "
        + "    WHERE A.artistId = AG.artistId AND A.artistId != " + artistId + " AND AG.genreId IN "
        + "    ( "
        + "        SELECT AG1.genreId AS genreId FROM artist_genre AG1 WHERE AG1.artistId = " + artistId
        + "    ) "
        + "    GROUP BY A.artistId ORDER BY Similarity DESC) AS Sim WHERE Sim.Similarity > 1;";
    return artistRepository.getSimilarArtists(query);
  }

  public Iterable<Artist> getAllArtists() {
    return artistRepository.findAll();
  }

  //** SONG **//

  public boolean checkIfSongIsSaved(long accountId, long songId) {
    return (songRepository.checkIfSongisSaved(accountId, songId) != null);
  }

  public boolean saveSongToMusic(long accountId, long songId) {
    return (songRepository.saveSongToMusic(accountId, songId) == 1);
  }

  public boolean removeSongFromMusic(long accountId, long songId) {
    return (songRepository.removeSongFromMusic(accountId, songId) == 1);
  }

  //** PLAYLIST **//

  public List<LibraryPlaylist> getCreatedAndFollowedPlaylists(long accountId) {
    try {
      return playlistRepository.findCreatedAndFollowedPlaylists(accountId);
    } catch (Exception e) {
      return null;
    }
  }

  public LibraryPlaylist getPlaylist(long accountId, long playlistId) {
//    LibraryPlaylist playlistToReturn = playlistRepository.findByPlaylistId(playlistId);
//    if (playlistToReturn != null) {
//      JsonObject obj = new JsonObject();
//      obj.addProperty("playlistId", playlistToReturn.getPlaylistId());
//      obj.addProperty("name", playlistToReturn.getName());
//      obj.addProperty("description", playlistToReturn.getDescription());
//      obj.addProperty("isPublic", playlistToReturn.getIsPublic());
//      obj.addProperty("accountId", playlistToReturn.getAccountId());
//      obj.addProperty("username", playlistToReturn.getUsername());
//      if (playlistRepository.checkIfPlaylistIsFollowed(accountId, playlistId)) {
//        obj.addProperty("followed", true);
//        return obj;
//      } else {
//        obj.addProperty("followed", false);
//        return obj;
//      }
//    } else {
//      return null;
//    }
    try {
      return playlistRepository.findByPlaylistId(playlistId);
    } catch (Exception e) {
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
    if (playlistRepository.save(playlist) != null) {
      return buildPlaylistJSON(playlist);
    } else {
      playlistRepository.deletePlaylistById(playlist.getPlaylistId());
      return null;
    }
  }

  public List<LibraryPlaylist> getCreatedPlaylists(long accountId) {
    try {
      return playlistRepository.findCreatedPlaylists(accountId);
    } catch (Exception e) {
      return null;
    }
  }

  public List<Long> getPlaylistFollowedIds(long accountId) {
    try {
      return playlistRepository.getListOfPlaylistsFollowed(accountId);
    } catch (Exception e) {
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

  public boolean changePlaylistFollowStatus(long accountId, long playlistId, boolean status) {
    if (status) {
      return (playlistRepository.followPlaylist(playlistId, accountId) == 1);
    } else {
      return (playlistRepository.unfollowPlaylist(playlistId, accountId) == 1);
    }
  }

  public boolean deleteSongFromPlaylist(long playlistId, long songId) {

    int tst = playlistRepository.deleteSongFromPlaylist(playlistId, songId);
    System.out.println(tst);
    return (tst == 1);
  }

  public boolean addSongToPlaylist(long playlistId, long songId) {

    return (playlistRepository.addSongToPlaylist(playlistId, songId) == 1);
  }

  public boolean editCustomerPlaylist(Playlist playlistToEdit) {
    Playlist playlist = playlistRepository.findOne(playlistToEdit.getPlaylistId());
    if (playlist != null) {
      try {
        playlist.setName(playlistToEdit.getName());
        playlistRepository.save(playlist);
        return true;
      } catch (Exception e) {
        return false;
      }
    } else {
      return false;
    }
  }

  public boolean verifyPlaylistExists(long playlistId) {
    return playlistRepository.exists(playlistId);
  }

  public List<LibraryPlaylist> getProfilePlaylists(long accountId) {
    try {
      return playlistRepository.findPublicPlaylistsByAccountId(accountId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  //** EVENT **//

  public Event getEvent(long eventId) {
    return eventRepository.findOne(eventId);
  }

  //** PROFILE **//
  public Profile getCustomerProfile(long accountId) {
    Profile profile = customerRepository.findCustomerProfile(accountId);
    if (profile != null && (profile.getRole().equals("PremiumUser") || profile.getRole().equals("BasicUser"))) {
      if (!profile.getPublicProfile()) {
        // Private profile; remove follower data
        profile.setFollowerCount(0);
        profile.setFollowingCount(0);
      }
      return profile;
    } else {
      return null;
    }
  }

  public List<Profile> getProfileFollowers(long accountId) {
    return customerRepository.findProfileFollowers(accountId);
  }

  public List<Profile> getProfileFollowing(long accountId) {
    return customerRepository.findProfileFollowing(accountId);
  }
}
