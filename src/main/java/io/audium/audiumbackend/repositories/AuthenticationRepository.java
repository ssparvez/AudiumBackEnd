package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.projections.LoginInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(excerptProjection = LoginInfo.class)
public interface AuthenticationRepository extends CrudRepository<Account, String> {
    @Transactional(readOnly = true)
    @Query("SELECT A.accountId AS accountId, A.role AS role, A.passwordHash AS passwordHash FROM Account A WHERE (A.username = ?1 OR A.email = ?1)")
    public LoginInfo findLoginInfoByUsernameOrEmail(String usernameOrEmail);
}
