package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Playlist;

import java.util.List;

public interface PlaylistRepositoryCustom {
  List<Playlist> searchPlaylists(String query);
}
