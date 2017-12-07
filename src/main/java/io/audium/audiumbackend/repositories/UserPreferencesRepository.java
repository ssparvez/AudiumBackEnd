package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.UserPreferences;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserPreferencesRepository extends CrudRepository<UserPreferences, Long> {

  @Transactional(readOnly = true)
  @Query("SELECT P FROM UserPreferences P WHERE P.accountId = ?1")
  public UserPreferences findUserPreferencesByAccountId(Long accountId);
}
