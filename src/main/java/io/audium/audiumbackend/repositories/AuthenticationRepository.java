package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthenticationRepository extends CrudRepository<Account, String> {



   @Query("SELECT A.accountid, A.role FROM Account A WHERE A.username = ?1 and A.password = ?2")
    public List<Object[]> verifyLoginInfo(String username, String password);


}
