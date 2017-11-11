package io.audium.audiumbackend.repositories;


import io.audium.audiumbackend.entities.Playlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, String> {
  @Transactional
  public Playlist findByPlaylistId(long playlistId);

  @Query("SELECT P FROM PlaylistFollower  PF, Playlist P WHERE PF.accountId = ?1 AND P.playlistId = PF.playlistId")
  public List<Playlist> findCustomerPlaylists(long accountId);

}
