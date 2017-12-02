package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Genre> searchGenres(String query) {
    return entityManager.createNativeQuery(query, "SearchGenreMapping").getResultList();
  }
}
