package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends CrudRepository<Account, Long> {


    @Transactional
    @Modifying
    @Query("DELETE FROM Account A WHERE A.accountId = ?1")
    public void deleteById(Long id);

}
