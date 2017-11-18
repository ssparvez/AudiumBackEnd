package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.projections.CustomerFollower;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  @Transactional
  public Customer deleteByAccountId(long accountId);

  @Transactional(readOnly = true)
  public Customer findByAccountId(long accountId);

  @Query("SELECT F.accountId AS accountId, F.username AS username, F.role AS role FROM Customer AS C INNER JOIN C.followers AS F WHERE C.accountId = ?1")
  public List<CustomerFollower> findCustomerFollowers(long accountId);

  @Query("SELECT F.accountId AS accountId, F.username AS username, F.role AS role FROM Customer AS C INNER JOIN C.following AS F WHERE C.accountId = ?1")
  public List<CustomerFollower> findCustomerFollowing(long accountId);
}
