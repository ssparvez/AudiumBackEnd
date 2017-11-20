package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "CustomerFollower", types = {Customer.class})
public interface CustomerFollower {
  Long getAccountId();
  String getUsername();
  String getRole();
}
