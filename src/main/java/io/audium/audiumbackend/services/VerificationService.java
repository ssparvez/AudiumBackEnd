package io.audium.audiumbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class VerificationService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer verifyIntegrityCustomerAccount(String token, long id) {
        try {
            token = token.substring(token.indexOf(" ") + 1);
            Customer account = customerRepository.findOne(id);

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

    public DecodedJWT returnDecodedToken(String token) {
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

    public String returnTokenClaimValue(String token, String claim) {

        return returnDecodedToken(token).getClaim(claim).asString();
    }

    public String returnClaimValueUsingDecodedToken(DecodedJWT decodedToken, String claim) {
        return decodedToken.getClaim(claim).asString();
    }
}
