package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Customer;

import java.util.List;

public interface CustomerRepositoryCustom {
  List<Customer> searchCustomers(String query);
}
