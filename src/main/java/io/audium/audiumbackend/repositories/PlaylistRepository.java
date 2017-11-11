package io.audium.audiumbackend.repositories;


import io.audium.audiumbackend.entities.Playlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PlaylistRepository extends CrudRepository<Playlist, String> {
  @Transactional
  public Playlist findByPlaylistid(long playlistid);

  @Query("select P from PlaylistFollower  C, Playlist P where C.accountId = ?1 and P.playlistid = C.playlistid")
  public List<Playlist> findCustomerPlaylists(long accountId);

}
