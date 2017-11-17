package io.audium.audiumbackend.services;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.PaymentInfo;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.CustomerRepository;
import io.audium.audiumbackend.repositories.PaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AccountService {
  static final String PREMIUM = "PremiumUser";
  static final String BASIC   = "BasicUser";
  @Autowired
  private AccountRepository     accountRepo;
  @Autowired
  private CustomerRepository    customerRepo;
  @Autowired
  private PaymentInfoRepository paymentRepo;
  @Autowired
  private VerificationService   verify;
  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public void registerCustomer(Customer customer) {
    customer.setRole("BasicUser");
    customer.setIsActive(true);
    customer.setPasswordHash(passwordEncoder.encode(customer.getPasswordHash()));
    customerRepo.save(customer);
  }

  public void deleteAccount(Long accountId) {
    accountRepo.deleteById(accountId);
  }

  public JsonObject updateCustomerInfo(Customer accountToSave, Customer savedAccount) {

    savedAccount.setFirstName(accountToSave.getFirstName());
    savedAccount.setLastName(accountToSave.getLastName());
    savedAccount.setGender(accountToSave.getGender());
    savedAccount.setDateOfBirth(accountToSave.getDateOfBirth());

    if (customerRepo.save(savedAccount) != null) {
      String token = verify.createCustomerToken(savedAccount);
      if (token != null) {
        JsonObject jsonToken = new JsonObject();
        jsonToken.addProperty("token", verify.createCustomerToken(savedAccount));
        return jsonToken;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public boolean changePassword(String oldPassword, String newPassword, Long accountId) {
    Account account = accountRepo.findPassHashByAccountId(accountId);
    if (passwordEncoder.matches(oldPassword, account.getPasswordHash())) {
      account.setPasswordHash(passwordEncoder.encode(newPassword));
      accountRepo.save(account);
      return true;
    } else {
      return false;
    }
  }

  public JsonObject getPaymentInfo(Long accountId) {
    PaymentInfo info = paymentRepo.findOne(accountId);
    if (info != null) {
      JsonObject obj = new JsonObject();
      Calendar   cal = Calendar.getInstance();
      cal.setTime(info.getCreditCardExpiration());
      obj.addProperty("ccNumber", verify.aesDecrypt(accountId, info.getCreditCardHash()));
      obj.addProperty("ccMonth", cal.get(Calendar.MONTH));
      obj.addProperty("ccYear", cal.get(Calendar.YEAR));
      obj.addProperty("zipCode", info.getZipCode());
      return obj;
    } else {
      return null;
    }
  }

  public JsonObject upgradeAccount(PaymentInfo info) {
    info.setCreditCardHash(verify.aesEncrypt(info.getAccountId(), info.getCreditCardHash()));
    if (paymentRepo.save(info) != null) {
      Customer account = customerRepo.findOne(info.getAccountId());
      account.setRole(PREMIUM);
      customerRepo.save(account);
      return createJsonToken(account);
    } else {
      return null;
    }
  }

  public JsonObject downgradeAccount(Long accountId) {
    Customer account = customerRepo.findOne(accountId);
    if (paymentRepo.deletePaymentInfo(accountId) != null) {
      account.setRole(BASIC);
      customerRepo.save(account);
      return createJsonToken(account);
    } else {
      return null;
    }
  }

  public boolean editPaymentInfo(PaymentInfo info) {
    info.setCreditCardHash(verify.aesEncrypt(info.getAccountId(), info.getCreditCardHash()));
    if (paymentRepo.save(info) != null) {
      return true;
    } else {
      return false;
    }
  }

  private JsonObject createJsonToken(Customer account) {
    String token = verify.createCustomerToken(account);
    if (token != null) {
      JsonObject obj = new JsonObject();
      obj.addProperty("token", token);
      return obj;
    } else {
      return null;
    }
  }
}
