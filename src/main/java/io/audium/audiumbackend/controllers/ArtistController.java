package io.audium.audiumbackend.controllers;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.projections.LibraryAlbum;
import io.audium.audiumbackend.services.ArtistManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistController {
  @Autowired
  ArtistManagementService artistManagementService;

  @GetMapping(value = "/artists/accounts/{accountId}/albums")
  public List<LibraryAlbum> getArtistAlbums(@PathVariable long accountId) {
    return artistManagementService.getArtistAlbums(accountId);
  }

  @DeleteMapping(value = "/albums/{albumId}")
  public void removeAlbum(@PathVariable long albumId) {
    artistManagementService.removeAlbum(albumId);
  }

  @DeleteMapping(value = "/songs/{songId}")
  public void removeSong(@PathVariable long songId) {
    artistManagementService.removeSong(songId);
  }
}
