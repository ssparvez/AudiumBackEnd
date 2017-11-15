package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, String> {
}
