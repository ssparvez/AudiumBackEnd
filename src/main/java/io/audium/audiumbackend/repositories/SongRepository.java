package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface SongRepository extends CrudRepository<Song, String> {

    @Transactional
    public Song findBySongid(long songid);

    @Query("select s from Song s inner join customer_song c on c.accountid = ?1")
    public List<Song> findCustomerSongs(long accountid);
}


//    SELECT people.fname, hats.hat
//        FROM people
//        INNER JOIN hats_collection
//        ON hats_collection.person_id = people.person_id