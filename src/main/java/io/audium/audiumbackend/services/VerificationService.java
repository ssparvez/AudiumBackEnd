package io.audium.audiumbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.AuthenticationRepository;
import io.audium.audiumbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BouncyCastleAesGcmBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class VerificationService {
  @Autowired
  private AuthenticationRepository authenticationRepository;
  @Autowired
  private CustomerRepository       customerRepository;
  @Autowired
  private AccountRepository accountRepo;


  public Customer verifyIntegrityCustomerAccount(String token, long accountId) {
    try {
      token = token.substring(token.indexOf(" ") + 1);
      Customer  account   = customerRepository.findOne(accountId);
      Algorithm algorithm = Algorithm.HMAC256("cse308");
      JWTVerifier verifier = JWT.require(algorithm)
        .withClaim("username", account.getUsername())
        .withClaim("accountId", account.getAccountId())
        .withClaim("firstName", account.getFirstName())
        .withClaim("lastName", account.getLastName())
        .withClaim("email", account.getEmail())
        .withClaim("role", account.getRole())
        .withClaim("dob", account.getDateOfBirth().toString())
        .withClaim("gender", account.getGender())
        .withIssuer("audium")
        .build(); //Reusable verifier instance

      DecodedJWT jwt = verifier.verify(token);
      return account;
    } catch (UnsupportedEncodingException exception) {
      //UTF-8 encoding not supported
    } catch (JWTVerificationException exception) {
      //Invalid signature/claims
    }
    return null;
  }
  public Account verifyIntegrityAdminAccount(String token, long accountId) {
    try {
      token = token.substring(token.indexOf(" ") + 1);
      Account  account   = accountRepo.findOne(accountId);
      Algorithm algorithm = Algorithm.HMAC256("cse308");
      JWTVerifier verifier = JWT.require(algorithm)
        .withClaim("username", account.getUsername())
        .withClaim("accountId", account.getAccountId())
        .withClaim("firstName", account.getFirstName())
        .withClaim("lastName", account.getLastName())
        .withClaim("email", account.getEmail())
        .withClaim("role", account.getRole())
        .withIssuer("audium")
        .build(); //Reusable verifier instance

      DecodedJWT jwt = verifier.verify(token);
      return account;
    } catch (UnsupportedEncodingException exception) {
      //UTF-8 encoding not supported
    } catch (JWTVerificationException exception) {
      //Invalid signature/claims
    }
    return null;
  }
  public DecodedJWT decodeToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("cse308");
      JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer("audium")
        .build(); //Reusable verifier instance
      DecodedJWT jwt = verifier.verify(token);
      return jwt;
    } catch (UnsupportedEncodingException exception) {
      //UTF-8 encoding not supported
    } catch (JWTVerificationException exception) {
      //Invalid signature/claims
    }
    return null;
  }

  public String getTokenClaimValue(String token, String claim) {

    return decodeToken(token).getClaim(claim).asString();
  }

  public String getClaimValueUsingDecodedToken(DecodedJWT decodedToken, String claim) {
    return decodedToken.getClaim(claim).asString();
  }

  public String createCustomerToken(Customer account) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("cse308");
      String token = JWT.create()
        .withClaim("username", account.getUsername())
        .withClaim("accountId", account.getAccountId())
        .withClaim("firstName", account.getFirstName())
        .withClaim("lastName", account.getLastName())
        .withClaim("email", account.getEmail())
        .withClaim("role", account.getRole())
        .withClaim("dob", account.getDateOfBirth().toString())
        .withClaim("gender", account.getGender())
        .withClaim("isValid", account.getIsActive())
        .withIssuer("audium")
        //.withExpiresAt( new Date(1800000))
        .sign(algorithm);
      return token;
    } catch (UnsupportedEncodingException exception) {
      //UTF-8 encoding not supported
    } catch (JWTCreationException exception) {
      //Invalid Signing configuration / Couldn't convert Claims.
    }
    return null;
  }


  public String createAdminToken(Account account) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("cse308");
      String token = JWT.create()
        .withClaim("username", account.getUsername())
        .withClaim("accountId", account.getAccountId())
        .withClaim("firstName", account.getFirstName())
        .withClaim("lastName", account.getLastName())
        .withClaim("email", account.getEmail())
        .withClaim("role", account.getRole())
        .withClaim("isValid", account.getIsActive())
        .withIssuer("audium")
        //.withExpiresAt( new Date(1800000))
        .sign(algorithm);
      return token;
    } catch (UnsupportedEncodingException exception) {
      //UTF-8 encoding not supported
    } catch (JWTCreationException exception) {
      //Invalid Signing configuration / Couldn't convert Claims.
    }
    return null;
  }

  public String createArtistToken(Account account) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("cse308");
      String token = JWT.create()
        .withClaim("username", account.getUsername())
        .withClaim("accountId", account.getAccountId())
        .withClaim("firstName", account.getFirstName())
        .withClaim("lastName", account.getLastName())
        .withClaim("email", account.getEmail())
        .withClaim("role", account.getRole())
        .withClaim("isValid", account.getIsActive())
        .withIssuer("audium")
        //.withExpiresAt( new Date(1800000))
        .sign(algorithm);
      return token;
    } catch (UnsupportedEncodingException exception) {
      //UTF-8 encoding not supported
    } catch (JWTCreationException exception) {
      //Invalid Signing configuration / Couldn't convert Claims.
    }
    return null;
  }

  public String aesEncrypt(long accountId, String sensitiveData) {
    Object[] salt = authenticationRepository.findSaltByAccountId(accountId);
    if (salt != null) {
      Account        account       = customerRepository.findByAccountId(accountId);
      BytesEncryptor bcEncryptor   = new BouncyCastleAesGcmBytesEncryptor(account.getUsername(), salt[0].toString());
      byte[]         encryptedData = bcEncryptor.encrypt(sensitiveData.getBytes());
      return new String(Hex.encode(encryptedData));
    } else {
      return null;
    }
  }

  public String aesDecrypt(long accountId, String encryptedData) {
    Object[] salt = authenticationRepository.findSaltByAccountId(accountId);
    if (salt != null) {
      Account        account     = customerRepository.findByAccountId(accountId);
      BytesEncryptor bcEncryptor = new BouncyCastleAesGcmBytesEncryptor(account.getUsername(), salt[0].toString());
      System.out.println(encryptedData);
      return new String(bcEncryptor.decrypt(Hex.decode(encryptedData)));
    } else {
      return null;
    }
  }
}
