package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface CustomerAccountRepository extends CrudRepository<Customer, Long> {


    @Transactional
    public Customer deleteByAccountid(long id);

    @Transactional
    public Customer findByAccountid(long id);

}
