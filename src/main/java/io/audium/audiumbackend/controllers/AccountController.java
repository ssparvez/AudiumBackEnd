package io.audium.audiumbackend.controllers;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.PaymentInfo;
import io.audium.audiumbackend.entities.projections.CustomerFollower;
import io.audium.audiumbackend.services.AccountService;
import io.audium.audiumbackend.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private VerificationService verificationService;

  @RequestMapping(method = RequestMethod.POST, value = "/register")
  public ResponseEntity register(@RequestBody Customer customerAccount) {

    accountService.registerAccount(customerAccount);
    return ResponseEntity.status(HttpStatus.OK).body(true);
  }

  @GetMapping(value = "/paymentinfo/{accountId}", produces = ("application/json"))
  public ResponseEntity getPaymentInfo(@PathVariable Long accountId) {

    JsonObject info = accountService.getPaymentInfo(accountId);
    if (info == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(info.toString());
    }
  }

  @PostMapping(value = "/upgrade")
  public ResponseEntity upgrade(@RequestHeader(value = "Authorization") String token,
                                @RequestBody PaymentInfo paymentInfo) {
    if (verificationService.verifyIntegrityCustomerAccount(token, paymentInfo.getAccountId()) != null) {
      JsonObject tokenToReturn = accountService.upgradeAccount(paymentInfo);
      if (token != null) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenToReturn.toString());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @CrossOrigin
  @DeleteMapping(value = "/downgradeaccount/{accountId}")
  public ResponseEntity downgrade(@RequestHeader(value = "Authorization") String token,
                                  @PathVariable Long accountId) {

    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {

      JsonObject tokenToReturn = accountService.downgradeAccount(accountId);
      if (token != null) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenToReturn.toString());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @PostMapping(value = "/editpaymentinfo")
  public ResponseEntity editPaymentInfo(@RequestHeader(value = "Authorization") String token,
                                        @RequestBody PaymentInfo paymentInfo) {
    if (verificationService.verifyIntegrityCustomerAccount(token, paymentInfo.getAccountId()) != null) {

      if (accountService.editPaymentInfo(paymentInfo)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }
  @CrossOrigin
  @PutMapping(value = "/changepassword/{accountId}")
  public ResponseEntity changePassword(@RequestHeader(value = "Authorization") String token,
                                       @RequestBody LinkedHashMap<String, String> credentials,
                                       @PathVariable Long accountId) {
    ArrayList<String> creds = new ArrayList<>(credentials.values());

    if (verificationService.verifyIntegrityCustomerAccount(token, accountId) != null) {
      if (accountService.changePassword(creds.get(0), creds.get(1), accountId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }
  @CrossOrigin
  @DeleteMapping(value = "/register/{accountId}")
  public void deleteAccount(@PathVariable Long accountId) {
    accountService.deleteAccount(accountId);
  }

  @CrossOrigin
  @PutMapping(value = "/editcustomer")
  public ResponseEntity updateAccount(@RequestHeader(value = "Authorization") String token,
                                      @RequestBody Customer newAccount) {
    Customer oldAccount = verificationService.verifyIntegrityCustomerAccount(token, newAccount.getAccountId());
    if (oldAccount != null) {

      JsonObject tokenToReturn = accountService.updateCustomerAccount(newAccount, oldAccount);

      if (tokenToReturn != null) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenToReturn.toString());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @GetMapping(value = "/accounts/{accountId}/followers")
  public List<CustomerFollower> getCustomerFollowers(@PathVariable long accountId) {
    return accountService.getCustomerFollowers(accountId);
  }

  @GetMapping(value = "/accounts/{accountId}/following")
  public List<CustomerFollower> getCustomerFollowing(@PathVariable long accountId) {
    return accountService.getCustomerFollowing(accountId);
  }
}
