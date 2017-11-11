package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface SongRepository extends CrudRepository<Song, String> {

  @Transactional
  public Song findBySongid(long songid);

  @Query("SELECT S FROM CustomerSong  C, Song S WHERE C.accountId = ?1 AND S.songid = C.songid")
  public List<Song> findCustomerSongs(long accountId);
}
