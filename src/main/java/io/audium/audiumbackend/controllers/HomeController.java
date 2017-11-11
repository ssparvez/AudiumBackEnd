package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Genre;
import io.audium.audiumbackend.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping(value = "/genres")
    public List<Genre> getAllGenres() {
        return homeService.getAllGenres();
    }
}
