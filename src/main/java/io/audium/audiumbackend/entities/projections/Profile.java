package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.UserPreferences;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "Profile", types = {Customer.class, UserPreferences.class})
public interface Profile {
  long getAccountId();
  String getUsername();
  String getRole();
  boolean getPublicProfile();
  int getFollowerCount();
  int getFollowingCount();

  int setFollowerCount(int followerCount);
  int setFollowingCount(int followingCount);
}
