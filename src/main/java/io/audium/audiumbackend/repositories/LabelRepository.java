package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Label;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LabelRepository extends CrudRepository<Label, Long> {

  @Transactional(readOnly = true)
  @Query(value="SELECT P.accountId, P.company FROM Label L, Partner P WHERE L.accountId = P.accountId", nativeQuery = true)
  public Iterable<Object> getAllLabels();

}

