package io.audium.audiumbackend.services;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.PaymentInfo;
import io.audium.audiumbackend.entities.UserPreferences;
import io.audium.audiumbackend.entities.projections.CustomerFollower;
import io.audium.audiumbackend.repositories.AccountRepository;
import io.audium.audiumbackend.repositories.CustomerRepository;
import io.audium.audiumbackend.repositories.PaymentInfoRepository;
import io.audium.audiumbackend.repositories.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class AccountService {
  static final private String PREMIUM = "PremiumUser";
  static final private String BASIC   = "BasicUser";
  static final private String ADMIN   = "Admin";
  @Autowired
  private AccountRepository         accountRepository;
  @Autowired
  private CustomerRepository        customerAccountRepository;
  @Autowired
  private PaymentInfoRepository     paymentRepository;
  @Autowired
  private VerificationService       verificationService;
  @Autowired
  private UserPreferencesRepository preferencesRepository;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public void registerAccount(Customer customerAccount) {

    customerAccount.setRole(BASIC);
    customerAccount.setIsActive(true);
    customerAccount.setPasswordHash(passwordEncoder.encode(customerAccount.getPasswordHash()));
    customerAccountRepository.save(customerAccount);
  }

  public boolean deleteAccount(long accountId) {
    return (accountRepository.deleteById(accountId) == 1);
  }

  public JsonObject updateCustomerAccount(Customer accountToSave, Customer savedAccount) {

    savedAccount.setFirstName(accountToSave.getFirstName());
    savedAccount.setLastName(accountToSave.getLastName());
    savedAccount.setGender(accountToSave.getGender());
    savedAccount.setDateOfBirth(accountToSave.getDateOfBirth());

    if (customerAccountRepository.save(savedAccount) != null) {
      String token = verificationService.createCustomerToken(savedAccount);
      if (token != null) {
        JsonObject obj = new JsonObject();
        obj.addProperty("token", verificationService.createCustomerToken(savedAccount));
        return obj;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }
  public boolean changePassword(String oldPassword, String newPassword, Long accountId) {
    Account account = accountRepository.findPassHashByAccountId(accountId);
    if (passwordEncoder.matches(oldPassword, account.getPasswordHash())) {
      account.setPasswordHash(passwordEncoder.encode(newPassword));
      accountRepository.save(account);
      return true;
    } else {
      return false;
    }
  }

  public JsonObject getPaymentInfo(Long accountId) {
    PaymentInfo info = paymentRepository.findOne(accountId);
    if (info != null) {
      JsonObject obj = new JsonObject();
      Calendar   cal = Calendar.getInstance();
      cal.setTime(info.getCreditCardExpiration());
      obj.addProperty("ccNumber", verificationService.aesDecrypt(accountId, info.getCreditCardHash()));
      obj.addProperty("ccMonth", cal.get(Calendar.MONTH) + 1);
      obj.addProperty("ccYear", cal.get(Calendar.YEAR));
      obj.addProperty("zipCode", info.getZipCode());
      return obj;
    } else {
      return null;
    }
  }

  public JsonObject getPreferences(Long accountId) {
    UserPreferences preferences = preferencesRepository.findOne(accountId);
    if (preferences != null) {
      JsonObject obj = new JsonObject();
      obj.addProperty("accountId", preferences.getAccountId());
      obj.addProperty("language", preferences.getLanguage());
      obj.addProperty("quality", preferences.getQuality());
      obj.addProperty("publicProfile", preferences.getPublicProfile());
      obj.addProperty("defaultPublicSession", preferences.getDefaultPublicSession());
      obj.addProperty("showExplicitContent", preferences.getShowExplicitContent());
      return obj;
    } else {
      return null;
    }
  }

  public boolean updatePreferences(UserPreferences preferences) {
    if (preferencesRepository.save(preferences) != null) {
      return true;
    } else {
      return false;
    }
  }

  public JsonObject upgradeAccount(PaymentInfo paymentInfo) {
    paymentInfo.setCreditCardHash(verificationService.aesEncrypt(paymentInfo.getAccountId(), paymentInfo.getCreditCardHash()));
    if (paymentRepository.save(paymentInfo) != null) {
      Customer account = customerAccountRepository.findOne(paymentInfo.getAccountId());
      account.setRole(PREMIUM);
      customerAccountRepository.save(account);
      return createJsonToken(account);
    } else {
      return null;
    }
  }

  public JsonObject downgradeAccount(Long accountId) {
    Customer account = customerAccountRepository.findOne(accountId);

    if (paymentRepository.deletePaymentInfo(accountId) == 1) {
      account.setRole(BASIC);
      customerAccountRepository.save(account);
      return createJsonToken(account);
    } else {
      return null;
    }
  }

  public boolean editPaymentInfo(PaymentInfo paymentInfo) {
    paymentInfo.setCreditCardHash(verificationService.aesEncrypt(paymentInfo.getAccountId(), paymentInfo.getCreditCardHash()));
    if (paymentRepository.save(paymentInfo) != null) {
      return true;
    } else {
      return false;
    }
  }

  public List<CustomerFollower> getCustomerFollowers(long accountId) {
    return customerAccountRepository.findCustomerFollowers(accountId);
  }

  public List<CustomerFollower> getCustomerFollowing(long accountId) {
    return customerAccountRepository.findCustomerFollowing(accountId);
  }

  public boolean checkIfFollowing(long profileId, long accountId) {
    System.out.println(customerAccountRepository.checkIfFollowing(profileId, accountId));
    return (customerAccountRepository.checkIfFollowing(profileId, accountId) != null);
  }

  private JsonObject createJsonToken(Customer account) {
    String token = verificationService.createCustomerToken(account);
    if (token != null) {
      JsonObject obj = new JsonObject();
      obj.addProperty("token", token);
      return obj;
    } else {
      return null;
    }
  }

  public boolean changeProfileFollowStatus(long accountId, long profileId, boolean status) {
    if (status) {
      return (customerAccountRepository.followCustomer(profileId, accountId) == 1);
    } else {
      return (customerAccountRepository.unFollowCustomer(profileId, accountId) == 1);
    }
  }
}
