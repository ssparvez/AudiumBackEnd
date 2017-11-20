package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EventRepositoryImpl implements EventRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Event> searchEvents(String query) {
    return entityManager.createNativeQuery(query, "SearchEventMapping").getResultList();
  }
}
