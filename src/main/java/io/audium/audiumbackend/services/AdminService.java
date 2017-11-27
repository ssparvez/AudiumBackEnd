package io.audium.audiumbackend.services;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  @Autowired
  private AuthenticationRepository authenticationRepository;

  private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

 private  AccountRepository accountRepo;

  public AdminService( AccountRepository accountRepo) {
    this.accountRepo = accountRepo;
  }

  /* Testing function: Returns Bcrypt hash of a string */
  public JsonObject testBcrypt(String rawData) {
    String hash = bcryptEncoder.encode(rawData);
    System.out.println("Bcrypt test:\n\tRaw data=\"" + rawData + "\"\n\tHash=\"" + hash + "\"");
    JsonObject jsonHash = new JsonObject();
    jsonHash.addProperty("rawData", rawData);
    jsonHash.addProperty("hash", hash);
    return jsonHash;
  }

  /* Testing function: Returns unique salt of the specified account */
  public String getAccountSalt(long accountId) {
    Object[] salt = authenticationRepository.findSaltByAccountId(accountId);
    if (salt != null) {
      return salt[0].toString();
    } else {
      return null;
    }
  }


  public boolean deleteAccount(long accountId) {
    return (accountRepo.deleteById(accountId) == 1);
  }
}
