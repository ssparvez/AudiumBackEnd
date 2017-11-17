package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;

@Projection(name = "LibraryAlbum", types = {Album.class, Artist.class})
public interface LibraryAlbum {
  Long getAlbumId();
  String getAlbumTitle();
  Date getReleaseYear();
  Long getArtistId();
  String getArtistName();
}
