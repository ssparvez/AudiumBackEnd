package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Playlist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PlaylistRepositoryImpl implements PlaylistRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Playlist> searchPlaylists(String query) {
    return entityManager.createNativeQuery(query, "SearchPlaylistMapping").getResultList();
  }
}
