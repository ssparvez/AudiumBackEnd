package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Account;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "LoginInfo", types = {Account.class})
public interface LoginInfo {
    Long getAccountId();
    String getRole();
    String getPasswordHash();
}
