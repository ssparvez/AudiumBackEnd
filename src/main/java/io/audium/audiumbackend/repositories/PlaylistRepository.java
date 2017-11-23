package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.projections.LibraryPlaylist;
import io.audium.audiumbackend.repositories.custom.PlaylistRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long>, PlaylistRepositoryCustom {

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Customer C JOIN C.playlists P WHERE C.accountId = ?1 ORDER BY name ASC")
  public List<LibraryPlaylist> findFollowedPlaylists(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Customer C JOIN C.createdPlaylists P WHERE C.accountId = ?1 ORDER BY name ASC")
  public List<LibraryPlaylist> findCreatedPlaylists(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Playlist AS P WHERE P.playlistId IN (SELECT PF.playlistId FROM Customer AS C JOIN C.playlists AS PF WHERE C.accountId = ?1) OR P.creator.accountId = ?1 ORDER BY P.name ASC")
  public List<LibraryPlaylist> findCreatedAndFollowedPlaylists(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Playlist P WHERE P.playlistId = ?1")
  public LibraryPlaylist findByPlaylistId(long playlistId);

  @Transactional
  @Modifying
  @Query("DELETE FROM Playlist P WHERE P.playlistId = ?1")
  public int deletePlaylistById(long playlistId);

  @Transactional(readOnly = true)
  @Query(value = "SELECT PF.playlistId FROM Playlist_Follower PF WHERE PF.accountId = ?1 AND PF.playlistId = ?2", nativeQuery = true)
  public boolean checkIfPlaylistIsFollowed(long accountId, long playlistId);

  @Transactional
  @Modifying
  @Query(value="INSERT INTO Playlist_Follower VALUES(?1,?2)", nativeQuery = true)
  public int followPlaylist(long playlistId, long accountId);

  @Transactional
  @Modifying
  @Query(value="DELETE FROM Playlist_Follower WHERE playlistId = ?1 AND accountId = ?2", nativeQuery = true)
  public int unfollowPlaylist(long playlistId, long accountId);

  @Transactional
  @Modifying
  @Query(value="DELETE FROM Playlist_Song WHERE playlistId = ?1 AND songId = ?2", nativeQuery = true)
  public int deleteSongFromPlaylist(long playlistId, long songId);

  @Transactional
  @Modifying
  @Query(value="INSERT INTO Playlist_Song VALUES(?1,?2,0)", nativeQuery = true)
  public int addSongToPlaylist(long playlistId, long accountId);

  @Transactional(readOnly = true)
  @Query(value="SELECT P.playlistId FROM Playlist_Follower P WHERE P.accountId = ?1", nativeQuery = true)
  public List<Long> getListOfPlaylistsFollowed(long accountId);


}
