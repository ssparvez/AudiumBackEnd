package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.entities.CustomerAccount;
import io.audium.audiumbackend.services.AccountService;
import io.audium.audiumbackend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register( @RequestBody CustomerAccount customerAccount) {

        accountService.registerAccount(customerAccount);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


    @CrossOrigin
    @DeleteMapping(value = "/registerr/{id}")
    public void deleteAccount( @PathVariable Long id ) {
        accountService.deleteAccount(id);
    }

}
