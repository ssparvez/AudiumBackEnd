package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.projections.LibraryArtist;

import java.util.List;

public interface ArtistRepositoryCustom {
  List<Artist> searchArtists(String query);

  List<LibraryArtist> getSimilarArtists(String query);
}
