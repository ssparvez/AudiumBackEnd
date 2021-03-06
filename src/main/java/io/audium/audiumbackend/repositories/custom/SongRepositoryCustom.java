package io.audium.audiumbackend.repositories.custom;

import io.audium.audiumbackend.entities.Song;

import java.util.List;

public interface SongRepositoryCustom {
  List<Song> searchSongs(String query);

  List<Song> getMonthSongStats(String query);

  List<Song> findSongsByGenreId(String query);
}
