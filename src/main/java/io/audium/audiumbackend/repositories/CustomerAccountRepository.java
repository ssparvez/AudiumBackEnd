package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.CustomerAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Long> {


    public CustomerAccount deleteByAccountid(long id);

}
