package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Album;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AlbumRepositoryImpl implements AlbumRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Album> searchAlbums(String query) {
    return entityManager.createNativeQuery(query, "SearchAlbumMapping").getResultList();
  }
}
