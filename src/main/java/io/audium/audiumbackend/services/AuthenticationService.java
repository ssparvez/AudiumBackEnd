package io.audium.audiumbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.projections.LoginInfo;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.AuthenticationRepository;
import io.audium.audiumbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class AuthenticationService {
  static final private String PREMIUM = "PremiumUser";
  static final private String BASIC   = "BasicUser";
  static final private String ADMIN   = "Admin";
  static final private String ARTIST  = "Artist";

  @Autowired
  private AuthenticationRepository authenticationRepository;
  @Autowired
  private CustomerRepository       customerRepository;
  private final VerificationService verification;
  private final AccountRepository accountRepo;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public AuthenticationService(VerificationService verification, AccountRepository accountRepo) {
    this.verification = verification;
    this.accountRepo = accountRepo;
  }

  public String checkLoginInfo(String usernameOrEmail, String password) {
    LoginInfo loginInfo = authenticationRepository.findLoginInfoByUsernameOrEmail(usernameOrEmail);

    if (!(loginInfo == null) && passwordEncoder.matches(password, loginInfo.getPasswordHash())) {
      try {
        Algorithm algorithm = Algorithm.HMAC256("cse308");

        switch (loginInfo.getRole()) {
          case ADMIN:
            Account admin = accountRepo.findOne(loginInfo.getAccountId());
            return verification.createAdminToken(admin);

          case ARTIST:
            Account artist = accountRepo.findOne(loginInfo.getAccountId());
            return verification.createArtistToken(artist);

          default:
            Customer account = customerRepository.findOne(loginInfo.getAccountId());
            return verification.createCustomerToken(account);
        }
      } catch (UnsupportedEncodingException exception) {
        //UTF-8 encoding not supported
      } catch (JWTCreationException exception) {
        //Invalid Signing configuration / Couldn't convert Claims.
      }
      return null;
    } else {
      return null;
    }
  }
}
