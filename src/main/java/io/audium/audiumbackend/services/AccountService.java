    package io.audium.audiumbackend.services;

    import com.auth0.jwt.JWT;
    import com.auth0.jwt.algorithms.Algorithm;
    import com.auth0.jwt.exceptions.JWTCreationException;
    import io.audium.audiumbackend.entities.Customer;
    import io.audium.audiumbackend.repositories.AccountRepository;
    import io.audium.audiumbackend.repositories.CustomerAccountRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.io.UnsupportedEncodingException;

    @Service
    public class AccountService {

        @Autowired
        private AccountRepository accountRepo;

        @Autowired
        private CustomerAccountRepository customerAccountRepo;

        public void registerAccount(Customer customerAccount) {
            customerAccount.setRole("BasicUser");
            customerAccount.setIsactive(new Long(1));
            customerAccountRepo.save(customerAccount);
            // System.out.println(customerAccount.getAccountid().toString());

        }

        public void deleteAccount(Long id) {

            //Customer ca = customerAccountRepo.findOne(new Long(7));
            //System.out.println("hello");
            //System.out.println(ca.getSongs().get(0).getTitle());
            accountRepo.deleteById(id);
        }

        public String updateAccount(Customer accountToSave, Customer savedAccount) {

            if ( accountToSave.getRole().equals("BasicUser") || accountToSave.getRole().equals("PremiumUser") ) {
                savedAccount.setFirstname(accountToSave.getFirstname());
                savedAccount.setLastname(accountToSave.getLastname());
                savedAccount.setGender(accountToSave.getGender());
                savedAccount.setDateofbirth(accountToSave.getDateofbirth());
            }
            customerAccountRepo.save(savedAccount);
            try {
                Algorithm algorithm = Algorithm.HMAC256("cse308");
                switch(accountToSave.getRole()) {
                    case "BasicUser":
                        String token = JWT.create()
                                .withClaim("username", savedAccount.getUsername())
                                .withClaim("accountId", savedAccount.getAccountid())
                                .withClaim("firstName", savedAccount.getFirstname())
                                .withClaim("lastName", savedAccount.getLastname())
                                .withClaim("email", savedAccount.getEmail())
                                .withClaim("role", savedAccount.getRole())
                                .withClaim("dob", savedAccount.getDateofbirth().toString())
                                .withClaim("gender",savedAccount.getGender())
                                .withIssuer("audium")
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


    }
