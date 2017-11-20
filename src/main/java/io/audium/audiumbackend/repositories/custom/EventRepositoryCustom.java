package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Event;

import java.util.List;

public interface EventRepositoryCustom {
  List<Event> searchEvents(String query);
}
