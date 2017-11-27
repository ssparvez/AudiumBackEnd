package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Genre;
import io.audium.audiumbackend.entities.projections.PopularTrack;
import io.audium.audiumbackend.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @GetMapping(value = "/songs/top/{pageIndex}/{pageSize}")
  @ResponseBody
  public ResponseEntity<List<PopularTrack>> getTopSongs(@PathVariable int pageIndex, @PathVariable int pageSize) {
    if (pageIndex < 0 || pageSize <= 0) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    List<PopularTrack> results = homeService.getTopSongs(pageIndex, pageSize);
    if (results.size() == 0) {
      return new ResponseEntity(results, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(results, HttpStatus.OK);
  }

  @GetMapping(value = "/accounts/{accountId}/albums/recent/{pageIndex}/{pageSize}")
  public ResponseEntity<List<PopularTrack>> getCustomerAlbumSongPlays(@PathVariable long accountId, @PathVariable int pageIndex, @PathVariable int pageSize) {
    if (pageIndex < 0 || pageSize <= 0) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    List<PopularTrack> results = homeService.getCustomerAlbumSongPlays(accountId, pageIndex, pageSize);
    if (results.size() == 0) {
      return new ResponseEntity(results, HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(results, HttpStatus.OK);
  }
}
