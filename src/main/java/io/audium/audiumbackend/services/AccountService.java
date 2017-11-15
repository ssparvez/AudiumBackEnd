package io.audium.audiumbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.PaymentInfo;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.CustomerAccountRepository;
import io.audium.audiumbackend.repositories.PaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private CustomerAccountRepository customerAccountRepo;
    @Autowired
    private PaymentInfoRepository paymentRepo;
    @Autowired
    private VerificationService verify;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    static final String PREMIUM = "PremiumUser";
  static final String BASIC = "BasicUser";


    public void registerAccount(Customer customerAccount) {

        customerAccount.setRole("BasicUser");
        customerAccount.setIsActive(new Long(1));
        customerAccount.setPasswordHash(passwordEncoder.encode(customerAccount.getPasswordHash()));
        customerAccountRepo.save(customerAccount);
    }

    public void deleteAccount(Long id) {
        accountRepo.deleteById(id);
    }

    public JsonObject updateCustomerAccount(Customer accountToSave, Customer savedAccount) {

        savedAccount.setFirstName(accountToSave.getFirstName());
        savedAccount.setLastName(accountToSave.getLastName());
        savedAccount.setGender(accountToSave.getGender());
        savedAccount.setDateOfBirth(accountToSave.getDateOfBirth());

        if(customerAccountRepo.save(savedAccount) != null) {
          String token = verify.createCustomerToken(savedAccount);
          if ( token != null) {
            JsonObject obj = new JsonObject();
            obj.addProperty("token", verify.createCustomerToken(savedAccount));
            return obj;
          }
          else return  null;
        }
        else {
          return null;
        }
    }


    public boolean changePassword(String oldPassword, String newPassword, Long id) {
      Account account = accountRepo.findPassHashByAccountId(id);
      if ( passwordEncoder.matches(oldPassword, account.getPasswordHash()) ) {
        account.setPasswordHash(passwordEncoder.encode(newPassword));
        accountRepo.save(account);
        return true;

      }
      else return false;
    }

    public JsonObject getPaymentInfo(Long id) {
      PaymentInfo info = paymentRepo.findOne(id);
      if ( info != null) {
        JsonObject obj = new JsonObject();
        Calendar cal = Calendar.getInstance();
        cal.setTime(info.getCreditcardexpire());
        obj.addProperty("ccNumber", verify.aesDecrypt(id,Hex.decode(info.getCreditcardhash())));
        obj.addProperty("ccMonth",cal.get(Calendar.MONTH));
        obj.addProperty("ccYear",cal.get(Calendar.YEAR));
        obj.addProperty("zipCode", info.getZipcode());
        return obj;
        }
        else return null;
    }

    public JsonObject upgradeAccount(PaymentInfo info) {
      info.setCreditcardhash(verify.aesEncrypt(info.getAccountId(),info.getCreditcardhash()));
      if (paymentRepo.save(info) != null) {
        Customer account = customerAccountRepo.findOne(info.getAccountId());
        account.setRole(PREMIUM);
        customerAccountRepo.save(account);
        return createJsonToken(account);
      }
      else return null;
    }

    public JsonObject downgradeAccount(Long accountId) {
      Customer account = customerAccountRepo.findOne(accountId);
      if( paymentRepo.deletePaymentInfo(accountId) != null) {
        account.setRole(BASIC);
        customerAccountRepo.save(account);
        return createJsonToken(account);
      }
      else return null;
    }


    public boolean editPaymentInfo(PaymentInfo info) {
      info.setCreditcardhash(verify.aesEncrypt(info.getAccountId(),info.getCreditcardhash()));
      if (paymentRepo.save(info) != null) {
        return true;
      }
      else
        return false;
    }

    private JsonObject createJsonToken(Customer account) {
      String token = verify.createCustomerToken(account);
      if ( token != null) {
        JsonObject obj = new JsonObject();
        obj.addProperty("token",token);
        return obj;
      }
      else return null;
    }
}
