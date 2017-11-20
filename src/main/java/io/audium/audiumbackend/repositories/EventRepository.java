package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Event;
import io.audium.audiumbackend.repositories.custom.EventRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long>, EventRepositoryCustom {
}
