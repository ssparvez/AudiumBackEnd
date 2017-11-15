package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.projections.LoginInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AuthenticationRepository extends CrudRepository<Account, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT A.accountId AS accountId, A.role AS role, A.passwordHash AS passwordHash FROM Account A WHERE (A.username = ?1 OR A.email = ?1)")
    public LoginInfo findLoginInfoByUsernameOrEmail(String usernameOrEmail);

    @Transactional(readOnly = true)
    @Query(value = "SELECT AccSalt.salt FROM AccountSalt AccSalt WHERE AccSalt.accountId = ?1", nativeQuery = true)
    public Object[] findSaltByAccountId(Long accountId);
}

