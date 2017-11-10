package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.services.AccountService;
import io.audium.audiumbackend.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private VerificationService verify = new VerificationService();


    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register( @RequestBody Customer customerAccount) {

        accountService.registerAccount(customerAccount);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


    @CrossOrigin
    @DeleteMapping(value = "/registerr/{id}")
    public void deleteAccount( @PathVariable Long id ) {
        accountService.deleteAccount(id);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, value = "/editcustomer")
    public ResponseEntity updateAccount(@RequestHeader(value="Authorization") String token,
                                        @RequestBody Customer newAccount) {

        Customer oldAccount = verify.verifyIntegrityCustomerAccount(token,newAccount.getAccountid());
        if (oldAccount != null ) {

            String tokenToReturn = accountService.updateAccount(newAccount, oldAccount);

            if ( tokenToReturn != null) {
                return ResponseEntity.status(HttpStatus.OK).body(tokenToReturn);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

    }



}
