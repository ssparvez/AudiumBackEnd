package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.projections.LibraryPlaylist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

    //@Transactional(readOnly = true)
    //public Playlist findByPlaylistId(long playlistId);

    @Transactional(readOnly = true)
    @Query("SELECT LibP.playlistId AS playlistId, LibP.name AS name, LibP.description AS description, LibP.isPublic AS isPublic, LibP.creator.accountId AS accountId, LibP.creator.username AS username FROM Customer C JOIN C.playlists LibP WHERE C.accountId = ?1 ORDER BY name ASC")
    public List<LibraryPlaylist> findFollowedPlaylists(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT LibP.playlistId AS playlistId, LibP.name AS name, LibP.description AS description, LibP.isPublic AS isPublic, LibP.creator.accountId AS accountId, LibP.creator.username AS username FROM Customer C JOIN C.createdPlaylists LibP WHERE C.accountId = ?1 ORDER BY name ASC")
  public List<LibraryPlaylist> findCreatedPlaylists(long accountId);

//  @Transactional(readOnly = true)
//  public List<LibraryPlaylist> findCreatedAndFollowedPlaylists (long accountId);

    @Transactional(readOnly = true)
    @Query("SELECT P.playlistId AS playlistId, P.name AS name, P.description AS description, P.isPublic AS isPublic, P.creator.accountId AS accountId, P.creator.username AS username FROM Playlist P WHERE P.playlistId = ?1")
    public LibraryPlaylist findByPlaylistId(long playlistId);

    //@Transactional(readOnly = true)
    //public Playlist findByPlaylistId(long playlistId);
}
