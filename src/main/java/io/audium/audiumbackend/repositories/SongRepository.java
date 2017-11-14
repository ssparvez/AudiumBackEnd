package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, String> {

    @Transactional(readOnly = true)
    public Song findBySongId(long songId);

    @Query("SELECT CS.song FROM CustomerSong CS WHERE CS.customer.accountId = ?1")
    public List<Song> findCustomerSongs(long accountId);
}
