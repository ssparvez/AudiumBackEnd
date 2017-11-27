package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Genre;
import io.audium.audiumbackend.entities.projections.PopularTrack;
import io.audium.audiumbackend.repositories.GenreRepository;
import io.audium.audiumbackend.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
  @Autowired
  GenreRepository genreRepository;
  @Autowired
  SongRepository  songRepository;

  public List<Genre> getAllGenres() {
    List<Genre> genres = new ArrayList<>();
    genreRepository.findAll().forEach(genres::add);
    return genres;
  }

  public List<PopularTrack> getCustomerAlbumSongPlays(long accountId, int pageIndex, int pageSize) {
    List<PopularTrack> results = songRepository.findCustomerAlbumSongPlays(accountId);

    return HelperService.getResultsPage(pageIndex, pageSize, results);
  }

  public List<PopularTrack> getTopSongs(int pageIndex, int pageSize) {
    List<PopularTrack> results = songRepository.findTopSongs();

    return HelperService.getResultsPage(pageIndex, pageSize, results);
  }
}
