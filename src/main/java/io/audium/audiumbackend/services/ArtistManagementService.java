package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.LibraryAlbum;
import io.audium.audiumbackend.repositories.AlbumRepository;
import io.audium.audiumbackend.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistManagementService {

  @Autowired
  AlbumRepository albumRepository;
  @Autowired
  SongRepository  songRepository;

  public List<LibraryAlbum> getArtistAlbums(long accountId) {
    return albumRepository.findArtistAlbumsByAccountId(accountId);
  }

  public List<Song> getArtistMonthStats(long artistAccountId) {
    String query = "SELECT S.songId AS songId, S.title AS title, A.artistId AS artistId, A.name AS artistName, LM.playCountLastMonth AS playCountLastMonth "
      + " FROM Song S, Artist A, artist_song ArtS, (SELECT S1.songId AS songId, COUNT(SP.songId) AS playCountLastMonth FROM Song AS S1 JOIN song_play AS SP ON S1.songId = SP.songId "
      + " WHERE YEAR(SP.timePlayed) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) AND MONTH(SP.timePlayed) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) GROUP BY S1.songId) AS LM WHERE S.songId = LM.songId "
      + " AND A.artistId = ArtS.artistId AND S.songId = ArtS.songId AND A.accountId = " + artistAccountId + " ORDER BY playCountLastMonth DESC";
    return songRepository.getMonthSongStats(query);
  }

  public void removeAlbum(long albumId) {
    albumRepository.delete(albumId);
  }

  public void removeSong(long songId) {
    albumRepository.delete(songId);
  }
}
