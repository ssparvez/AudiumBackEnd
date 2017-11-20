package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Artist;

import java.util.List;

public interface ArtistRepositoryCustom {
  List<Artist> searchArtists(String query);
}
