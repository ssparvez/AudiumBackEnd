package io.audium.audiumbackend.controllers;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.PaymentInfo;
import io.audium.audiumbackend.services.AccountService;
import io.audium.audiumbackend.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@RestController
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private VerificationService verify;

  @RequestMapping(method = RequestMethod.POST, value = "/register")
  public ResponseEntity register(@RequestBody Customer customer) {

    accountService.registerCustomer(customer);
    return ResponseEntity.status(HttpStatus.OK).body(true);
  }

  @GetMapping(value = "/paymentinfo/{id}", produces = ("application/json"))
  public ResponseEntity getPaymentInfo(@PathVariable Long id) {

    JsonObject info = accountService.getPaymentInfo(id);
    if (info == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(info.toString());
    }
  }

  @PostMapping(value = "/upgrade")
  public ResponseEntity upgrade(@RequestBody PaymentInfo paymentInfo) {
    JsonObject token = accountService.upgradeAccount(paymentInfo);
    if (token != null) {
      return ResponseEntity.status(HttpStatus.OK).body(token.toString());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
  }

  @DeleteMapping(value = "/downgradeaccount/{id}")
  public ResponseEntity downgrade(@PathVariable Long id) {
    JsonObject token = accountService.downgradeAccount(id);
    if (token != null) {
      return ResponseEntity.status(HttpStatus.OK).body(token.toString());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
  }

  @PostMapping(value = "/editpaymentinfo")
  public ResponseEntity editPaymentInfo(@RequestHeader(value = "Authorization") String token,
                                        @RequestBody PaymentInfo paymentInfo) {
    if (verify.verifyIntegrityCustomerAccount(token, paymentInfo.getAccountId()) != null) {

      if (accountService.editPaymentInfo(paymentInfo)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @PutMapping(value = "/changepassword/{id}")
  public ResponseEntity changePassword(@RequestHeader(value = "Authorization") String token,
                                       @RequestBody LinkedHashMap<String, String> credentials,
                                       @PathVariable Long id) {
    ArrayList<String> creds = new ArrayList<>(credentials.values());
    if (verify.verifyIntegrityCustomerAccount(token, id) != null) {
      if (accountService.changePassword(creds.get(0), creds.get(1), id)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }
  @CrossOrigin
  @DeleteMapping(value = "/register/{id}")
  public void deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
  }

  @CrossOrigin
  @PutMapping(value = "/editcustomer")
  public ResponseEntity updateCustomerInfo(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody Customer newAccount) {
    Customer oldAccount = verify.verifyIntegrityCustomerAccount(token, newAccount.getAccountId());
    if (oldAccount != null) {

      JsonObject tokenToReturn = accountService.updateCustomerInfo(newAccount, oldAccount);

      if (tokenToReturn != null) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenToReturn.toString());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }
}
