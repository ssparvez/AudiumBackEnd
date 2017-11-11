package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String checkLoginInfo(@RequestBody LinkedHashMap<String, String> loginCredentials) {
//        String token = loginService.checkLoginInfo(account.getUsername(), account.getPasswordHash());
//        if ( token != null ) {
//            return ResponseEntity.status(HttpStatus.OK).body(token);
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//
//        }
    /* credentials[0] == username; credentials[1] == password */
        ArrayList<String> credentials = new ArrayList<>(loginCredentials.values());
        System.out.println("sp: " + credentials.get(0) + "  " + credentials.get(1));
        return authenticationService.checkLoginInfo(credentials.get(0), credentials.get(1));
    }

}
