package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Customer> searchCustomers(String query) {
    return entityManager.createNativeQuery(query, "SearchCustomerMapping").getResultList();
  }
}
