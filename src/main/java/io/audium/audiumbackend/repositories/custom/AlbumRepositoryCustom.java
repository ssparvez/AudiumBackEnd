package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Album;

import java.util.List;

public interface AlbumRepositoryCustom {
  List<Album> searchAlbums(String query);
}
