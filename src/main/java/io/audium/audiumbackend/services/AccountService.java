package io.audium.audiumbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class AccountService {
    @Autowired
    private AccountRepository  accountRepo;
    @Autowired
    private CustomerRepository customerRepo;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerAccount(Customer customerAccount) {

        customerAccount.setRole("BasicUser");
        customerAccount.setIsActive(new Long(1));
        customerAccount.setPasswordHash(passwordEncoder.encode(customerAccount.getPasswordHash()));
        customerRepo.save(customerAccount);
    }

    public void deleteAccount(Long id) {

        //Customer ca = customerRepo.findOne(new Long(7));
        //System.out.println(ca.getSongs().get(0).getTitle());
        accountRepo.deleteById(id);
    }

    public String updateCustomerAccount(Customer accountToSave, Customer savedAccount) {

        savedAccount.setFirstName(accountToSave.getFirstName());
        savedAccount.setLastName(accountToSave.getLastName());
        savedAccount.setGender(accountToSave.getGender());
        savedAccount.setDateOfBirth(accountToSave.getDateOfBirth());
        customerRepo.save(savedAccount);
        try {
            Algorithm algorithm = Algorithm.HMAC256("cse308");
            String token = JWT.create()
                .withClaim("username", savedAccount.getUsername())
                .withClaim("accountId", savedAccount.getAccountId())
                .withClaim("firstName", savedAccount.getFirstName())
                .withClaim("lastName", savedAccount.getLastName())
                .withClaim("email", savedAccount.getEmail())
                .withClaim("role", savedAccount.getRole())
                .withClaim("dob", savedAccount.getDateOfBirth().toString())
                .withClaim("gender", savedAccount.getGender())
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
}
