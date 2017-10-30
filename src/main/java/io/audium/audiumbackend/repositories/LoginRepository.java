package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<Account, String> {



   @Query("SELECT A FROM Account A WHERE A.username = ?1 and A.password = ?2")
    public Account checkLoginInfo(String username, String password);


}
