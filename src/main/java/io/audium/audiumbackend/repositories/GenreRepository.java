package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Genre;
import io.audium.audiumbackend.repositories.custom.GenreRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long>, GenreRepositoryCustom {
}
