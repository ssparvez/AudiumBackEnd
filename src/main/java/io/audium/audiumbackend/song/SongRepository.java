package io.audium.audiumbackend.song;

import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, String> {
}