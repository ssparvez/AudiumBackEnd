package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.*;
import io.audium.audiumbackend.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

  @Autowired
  private SearchService searchService;

  @GetMapping(value = "/search/songs/{searchQuery:.+}")
  public List<Song> searchSongs(@PathVariable String searchQuery) {
    return searchService.searchSongs(searchQuery);
  }

  @GetMapping(value = "/search/albums/{searchQuery:.+}")
  public List<Album> searchAlbums(@PathVariable String searchQuery) {
    return searchService.searchAlbums(searchQuery);
  }

  @GetMapping(value = "/search/artists/{searchQuery:.+}")
  public List<Artist> searchArtists(@PathVariable String searchQuery) {
    return searchService.searchArtists(searchQuery);
  }

  @GetMapping(value = "/search/playlists/{searchQuery:.+}")
  public List<Playlist> searchPlaylists(@PathVariable String searchQuery) {
    return searchService.searchPlaylists(searchQuery);
  }

  @GetMapping(value = "/search/events/{searchQuery:.+}")
  public List<Event> searchEvents(@PathVariable String searchQuery) {
    return searchService.searchEvents(searchQuery);
  }

  @GetMapping(value = "/search/profiles/{searchQuery:.+}")
  public List<Customer> searchCustomers(@PathVariable String searchQuery) {
    return searchService.searchCustomers(searchQuery);
  }

  @GetMapping(value = "/search/genres/{searchQuery:.+}")
  public List<Genre> searchGenres(@PathVariable String searchQuery) {
    return searchService.searchGenres(searchQuery);
  }
}
