package io.audium.audiumbackend.controllers;
import io.audium.audiumbackend.entities.Account;
import io.audium.audiumbackend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String checkLoginInfo( @RequestBody Account account) {
//        String token = loginService.checkLoginInfo(account.getUsername(), account.getPassword());
//        if ( token != null ) {
//            return ResponseEntity.status(HttpStatus.OK).body(token);
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//
//        }
        return authenticationService.checkLoginInfo(account.getUsername(), account.getPassword());
    }


}
