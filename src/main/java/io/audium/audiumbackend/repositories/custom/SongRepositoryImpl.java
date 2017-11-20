package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Song;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SongRepositoryImpl implements SongRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Song> searchSongs(String query) {
    return entityManager.createNativeQuery(query, "SearchSongMapping").getResultList();
  }
}
