package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.CustomerAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transactional;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Long> {


    @Transactional
    public CustomerAccount deleteByAccountid(long id);

    @Transactional
    public CustomerAccount findByAccountid( long id);

}
