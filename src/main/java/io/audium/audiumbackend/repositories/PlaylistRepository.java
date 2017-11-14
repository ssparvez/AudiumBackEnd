package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.projections.LibraryPlaylist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(excerptProjection = LibraryPlaylist.class)
public interface PlaylistRepository extends CrudRepository<Playlist, String> {

    @Transactional(readOnly = true)
    public Playlist findByPlaylistId(long playlistId);

    @Transactional(readOnly = true)
    @Query("SELECT LibP.playlistId AS playlistId, LibP.name AS name, LibP.description AS description FROM Customer C JOIN C.playlists LibP WHERE C.accountId = ?1")
    public List<LibraryPlaylist> findFollowerByAccountId(long accountId);
}
