package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Genre;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.relationships.SongPlay;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Projection(name = "RecentTrack", types = {Song.class, Artist.class, SongPlay.class, Album.class, Genre.class})
public interface RecentTrack {
  Long getSongId();
  String getTitle();
  Long getArtistId();
  String getArtistName();
  //List<Artist> getArtists();
  Long getAlbumId();
  String getAlbumTitle();
  String getFile();
  Time getDuration();
  boolean getIsExplicit();
  Date getYear();
  Long getGenreId();
  String getGenreName();
  Timestamp getTimePlayed();
}
