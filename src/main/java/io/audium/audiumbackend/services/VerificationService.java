package io.audium.audiumbackend.services;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

public class VerificationService {


    public VerificationService() {
    }


    public boolean verifyIntegrityWithRole(String token, String roleToCheck) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("cse308");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("role",roleToCheck)
                    .withIssuer("audium")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException exception){
            //UTF-8 encoding not supported
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return false;
    }

    public DecodedJWT returnDecodedToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("cse308");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("audium")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return  jwt;
        } catch (UnsupportedEncodingException exception){
            //UTF-8 encoding not supported
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return null;
    }
    public String returnTokenClaimValue(String token, String claim) {

        return  returnDecodedToken(token).getClaim(claim).asString();
    }

    public String returnClaimValueUsingDecodedToken( DecodedJWT decodedToken, String claim) {
        return decodedToken.getClaim(claim).asString();

    }

}
