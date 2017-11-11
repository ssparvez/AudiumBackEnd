package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface SongRepository extends CrudRepository<Song, String> {

    @Transactional
    public Song findBySongId(long songId);

    @Query("SELECT S FROM CustomerSong  CS, Song S WHERE CS.accountId = ?1 AND S.songId = CS.songId")
    public List<Song> findCustomerSongs(long accountId);
}
