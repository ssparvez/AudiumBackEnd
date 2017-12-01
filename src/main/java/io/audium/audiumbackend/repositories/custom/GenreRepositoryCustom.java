package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Genre;

import java.util.List;

public interface GenreRepositoryCustom {
  List<Genre> searchGenres(String query);
}
