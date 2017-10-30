package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.services.LoginService;
import io.audium.audiumbackend.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String checkLoginInfo(@RequestBody Account account) {
        return loginService.checkLoginInfo(account.getUsername(), account.getPassword());
    }


}
