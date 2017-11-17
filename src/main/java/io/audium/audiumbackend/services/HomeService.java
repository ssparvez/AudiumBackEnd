package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Genre;
import io.audium.audiumbackend.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
  @Autowired
  GenreRepository genreRepository;

  public List<Genre> getAllGenres() {
    List<Genre> genres = new ArrayList<>();
    genreRepository.findAll().forEach(genres::add); // this line gets from the db and converts data into objects
    for (Genre genre : genres) {
      System.out.println(genre.getGenreName());
    }
    return genres;
  }
}
