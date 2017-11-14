package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Transactional
    public Customer deleteByAccountId(long accountId);

    @Transactional(readOnly = true)
    public Customer findByAccountId(long accountId);
}
