package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.projections.LibraryPlaylist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Customer C JOIN C.playlists P WHERE C.accountId = ?1 ORDER BY name ASC")
  public List<LibraryPlaylist> findFollowedPlaylists(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Customer C JOIN C.createdPlaylists P WHERE C.accountId = ?1 ORDER BY name ASC")
  public List<LibraryPlaylist> findCreatedPlaylists(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Playlist AS P WHERE P.playlistId IN (SELECT PF.playlistId FROM Customer AS C JOIN C.playlists AS PF WHERE C.accountId = ?1) OR P.creator.accountId = ?1 ORDER BY P.name ASC")
  public List<LibraryPlaylist> findCreatedAndFollowedPlaylists(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Playlist P WHERE P.playlistId = ?1")
  public LibraryPlaylist findByPlaylistId(long playlistId);
}
