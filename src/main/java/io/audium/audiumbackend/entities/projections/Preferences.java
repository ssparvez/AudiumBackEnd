package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Account;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "Preferences", types = {Account.class})
public interface Preferences {
  Long getAccountId();
  String getUserPreferences();
}
