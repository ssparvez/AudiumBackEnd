package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
public class LoginController {

  @Autowired
  private AuthenticationService authenticationService;

  @CrossOrigin
  @PostMapping(value = "/login")
  public String checkLoginInfo(@RequestBody LinkedHashMap<String, String> loginCredentials) {
        /* credentials[0] == username; credentials[1] == password */
    ArrayList<String> credentials = new ArrayList<>(loginCredentials.values());
    return authenticationService.checkLoginInfo(credentials.get(0), credentials.get(1));
  }
}
