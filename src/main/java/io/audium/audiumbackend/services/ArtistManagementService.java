package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.projections.LibraryAlbum;
import io.audium.audiumbackend.repositories.AlbumRepository;
import io.audium.audiumbackend.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistManagementService {

  @Autowired
  AlbumRepository albumRepository;
  @Autowired
  SongRepository  songRepository;

  public List<LibraryAlbum> getArtistAlbums(long accountId) {
    return albumRepository.findArtistAlbumsByAccountId(accountId);
  }
  public void removeAlbum(long albumId) {
    albumRepository.delete(albumId);
  }

  public void removeSong(long songId) {
    albumRepository.delete(songId);
  }
}
