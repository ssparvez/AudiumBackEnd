package io.audium.audiumbackend.services;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;


    public String checkLoginInfo(String username, String password) {
        Account account = loginRepository.checkLoginInfo(username, password);

        if ( account != null) {
            try {
                Algorithm algorithm = Algorithm.HMAC256("cse308");
                String token = JWT.create()
                        .withClaim("username",account.getUsername())
                        .withClaim("accountID",account.getAccountid())
                        .withClaim("firstName",account.getFirstname())
                        .withClaim("lastName",account.getLastname())
                        .withClaim("email",account.getEmail())
                        .withClaim("role", account.getRole())
                        .withIssuer("audium")
                        //.withExpiresAt( new Date(1800000))
                        .sign(algorithm);
                return token;
            } catch (UnsupportedEncodingException exception){
                //UTF-8 encoding not supported
            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
            }
            return null;

        }
        else
            return null;
    }
}
