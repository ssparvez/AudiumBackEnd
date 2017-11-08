package io.audium.audiumbackend.services;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.CustomerAccount;
import io.audium.audiumbackend.repositories.AuthenticationRepository;
import io.audium.audiumbackend.repositories.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private CustomerAccountRepository customerAccountRepo;


    public String checkLoginInfo(String username, String password) {
       List<Object[]> lst = authenticationRepository.verifyLoginInfo(username, password);
        if ( !lst.isEmpty()) {

            try {
                Algorithm algorithm = Algorithm.HMAC256("cse308");

                switch(lst.get(0)[1].toString()) {
                    case "BasicUser":
                        CustomerAccount account = customerAccountRepo.findOne(new Long(lst.get(0)[0].toString()));
                        System.out.println(account.getDateofbirth());
                        String token = JWT.create()
                                .withClaim("username", account.getUsername())
                                .withClaim("accountId", account.getAccountid())
                                .withClaim("firstName", account.getFirstname())
                                .withClaim("lastName", account.getLastname())
                                .withClaim("email", account.getEmail())
                                .withClaim("role", account.getRole())
                                .withClaim("dob", account.getDateofbirth().toString())
                                .withIssuer("audium")
                                //.withExpiresAt( new Date(1800000))
                                .sign(algorithm);
                        return token;
                }
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
