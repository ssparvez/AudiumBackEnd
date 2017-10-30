package io.audium.audiumbackend.repositories;

import org.springframework.data.repository.CrudRepository;
import io.audium.audiumbackend.entities.Artist;

public interface ArtistRepository extends CrudRepository<Artist, String> {
}
