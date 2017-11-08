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
}
