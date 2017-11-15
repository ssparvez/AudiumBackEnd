package io.audium.audiumbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.projections.LoginInfo;
import io.audium.audiumbackend.repositories.AuthenticationRepository;
import io.audium.audiumbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BouncyCastleAesGcmBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private CustomerRepository       customerRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String checkLoginInfo(String usernameOrEmail, String password) {
        LoginInfo loginInfo = authenticationRepository.findLoginInfoByUsernameOrEmail(usernameOrEmail);

        if (!(loginInfo == null) && passwordEncoder.matches(password, loginInfo.getPasswordHash())) {
            try {
                Algorithm algorithm = Algorithm.HMAC256("cse308");

                switch (loginInfo.getRole()) {
                    default:
                        Customer account = customerRepository.findOne(loginInfo.getAccountId());

                        aesEncrypt(account.getAccountId(), "2345678901234567"); // @TODO: Delete this test statement
                        String token = JWT.create()
                            .withClaim("username", account.getUsername())
                            .withClaim("accountId", account.getAccountId())
                            .withClaim("firstName", account.getUsername())
                            .withClaim("lastName", account.getLastName())
                            .withClaim("email", account.getEmail())
                            .withClaim("role", account.getRole())
                            .withClaim("dob", account.getDateOfBirth().toString())
                            .withClaim("gender", account.getGender())
                            .withIssuer("audium")
                            //.withExpiresAt( new Date(1800000))
                            .sign(algorithm);
                        return token;
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

    public String aesEncrypt(long accountId, String sensitiveData) {
        Object[] salt = authenticationRepository.findSaltByAccountId(accountId);
        /* @TODO: Better system for encryption key (currently using username which is awful) */
        if (salt != null) {
            Account account = customerRepository.findByAccountId(accountId);
            System.out.print("Salt: " + salt[0].toString() + "   CC: " + sensitiveData);
            BytesEncryptor bcEncryptor   = new BouncyCastleAesGcmBytesEncryptor(account.getUsername(), salt[0].toString());
            byte[]         encryptedData = bcEncryptor.encrypt(sensitiveData.getBytes());
            System.out.println("    Encrypted: " + new String(Hex.encode(encryptedData)) + "   Decrypted: " + new String(bcEncryptor.decrypt(encryptedData)));
            return new String(Hex.encode(encryptedData));
        } else {
            return null;
        }
    }

    public String aesDecrypt(long accountId, byte[] encryptedData) {
        Object[] salt = authenticationRepository.findSaltByAccountId(accountId);
        /* @TODO: Better system for encryption key (currently using username which is awful) */
        if (salt != null) {
            Account        account       = customerRepository.findByAccountId(accountId);
            BytesEncryptor bcEncryptor   = new BouncyCastleAesGcmBytesEncryptor(account.getUsername(), salt[0].toString());
            String         sensitiveData = new String(bcEncryptor.decrypt(encryptedData));
            return sensitiveData;
        } else {
            return null;
        }
    }
}
