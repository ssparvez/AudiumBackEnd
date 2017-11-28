package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.AlbumTrack;
import io.audium.audiumbackend.entities.projections.LibrarySong;
import io.audium.audiumbackend.entities.projections.PlaylistTrack;
import io.audium.audiumbackend.entities.projections.PopularTrack;
import io.audium.audiumbackend.repositories.custom.SongRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long>, SongRepositoryCustom {

  @Transactional(readOnly = true)
  public Song findBySongId(long songId);

  @Transactional(readOnly = true)
  @Query("SELECT CS.song.songId AS songId, CS.song.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, Alb.albumId AS albumId, Alb.albumTitle AS albumTitle, CS.song.duration AS duration, CS.song.isExplicit AS isExplicit, CS.timeAdded AS timeAdded FROM Customer C INNER JOIN C.customerSongs CS INNER JOIN CS.song.artists Art INNER JOIN CS.song.albumSongs AlbSo INNER JOIN AlbSo.album Alb WHERE C.accountId = ?1 GROUP BY CS.song ORDER BY timeAdded DESC")
  public List<LibrarySong> findCustomerSongs(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT PS.song.songId AS songId, PS.song.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, Alb.albumId AS albumId, Alb.albumTitle AS albumTitle, PS.song.duration AS duration, PS.song.isExplicit AS isExplicit, PS.timeAdded AS timeAdded FROM PlaylistSong PS INNER JOIN PS.song.artists Art INNER JOIN PS.song.albumSongs AlbSo INNER JOIN AlbSo.album Alb WHERE PS.playlist.playlistId = ?1 GROUP BY PS.song ORDER BY timeAdded ASC")
  public List<PlaylistTrack> findPlaylistSongs(long playlistId);

  @Transactional(readOnly = true)
  @Query("SELECT AlbS.song.songId AS songId, AlbS.song.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, AlbS.album.albumId AS albumId, AlbS.album.albumTitle AS albumTitle, AlbS.song.duration AS duration, AlbS.song.isExplicit AS isExplicit, AlbS.trackNumber AS trackNumber FROM AlbumSong AlbS INNER JOIN AlbS.song.artists Art WHERE AlbS.album.albumId = ?1 GROUP BY AlbS.song ORDER BY trackNumber ASC")
  public List<AlbumTrack> findAlbumSongs(long albumId);

  @Query("SELECT S.songId AS songId, S.title AS title, AlbS.album.artist.artistId AS artistId, AlbS.album.artist.artistName AS artistName, AlbS.album.albumId AS albumId, AlbS.album.albumTitle AS albumTitle, S.duration AS duration, S.isExplicit AS isExplicit, S.year AS year, S.genre.genreId AS genreId, S.genre.genreName AS genreName, S.playCount AS playCount FROM SongPlay SP INNER JOIN SP.song S INNER JOIN S.albumSongs AlbS WHERE SP.customer.accountId = ?1 GROUP BY AlbS.album ORDER BY SP.timePlayed DESC")
  public List<PopularTrack> findCustomerAlbumSongPlays(long accountId);

  @Query("SELECT S.songId AS songId, S.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, AlbS.album.albumId AS albumId, AlbS.album.albumTitle AS albumTitle, S.duration AS duration, S.isExplicit AS isExplicit, S.year AS year, S.genre.genreId AS genreId, S.genre.genreName AS genreName, S.playCount AS playCount FROM SongPlay SP INNER JOIN SP.song S INNER JOIN S.artists Art INNER JOIN S.albumSongs AlbS WHERE SP.customer.accountId = ?1 GROUP BY S ORDER BY SP.timePlayed DESC")
  public List<PopularTrack> findCustomerSongPlays(long accountId);

  @Query("SELECT ArtS.songId AS songId, ArtS.title AS title, A.artistId AS artistId, A.artistName AS artistName, ArtAlbS.album.albumId AS albumId, ArtAlbS.album.albumTitle AS albumTitle, ArtS.duration AS duration, ArtS.isExplicit AS isExplicit, ArtS.year AS year, ArtS.genre.genreId AS genreId, ArtS.genre.genreName AS genreName, ArtS.playCount AS playCount FROM Artist A INNER JOIN A.songs ArtS INNER JOIN ArtS.albumSongs ArtAlbS WHERE A.artistId = ?1 GROUP BY ArtS ORDER BY playCount DESC")
  public List<PopularTrack> findArtistSongs(long artistId);

  @Query("SELECT S.songId AS songId, S.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, AlbS.album.albumId AS albumId, AlbS.album.albumTitle AS albumTitle, S.duration AS duration, S.isExplicit AS isExplicit, S.year AS year, S.genre.genreId AS genreId, S.genre.genreName AS genreName, S.playCount AS playCount FROM Song S INNER JOIN S.artists Art INNER JOIN S.albumSongs AlbS GROUP BY S ORDER BY playCount DESC, S.year DESC, S.title ASC")
  public List<PopularTrack> findTopSongs();

  @Transactional(readOnly = true)
  @Query(value = "SELECT CS.songId FROM Customer_Song CS WHERE CS.accountId = ?1 AND CS.songId = ?2", nativeQuery = true)
  public Object checkIfSongisSaved(long accountId, long songId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO Customer_Song VALUES(?1,?2,0)", nativeQuery = true)
  public int saveSongToMusic(long accountId, long songId);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM Customer_Song WHERE accountId = ?1 AND songId = ?2", nativeQuery = true)
  public int removeSongFromMusic(long accountId, long songId);

  @Transactional(readOnly = true)
  @Query(value = "SELECT CS.songId FROM Customer_Song CS WHERE CS.accountId = ?1", nativeQuery = true)
  public List<Long> getListOfSavedSongsIds(long accountId);

  @Transactional
  @Modifying
  @Query("DELETE FROM Song S WHERE S.songId = ?1")
  public int deleteById(long songId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO Artist_Song VALUES(?1,?2,1)", nativeQuery = true)
  public int linkSongToArtist(long artistId, long songId);


}
