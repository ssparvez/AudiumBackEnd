package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Artist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Artist> searchArtists(String query) {
    return entityManager.createNativeQuery(query, "SearchArtistMapping").getResultList();
  }
}
