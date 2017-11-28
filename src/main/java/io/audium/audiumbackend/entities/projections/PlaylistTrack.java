package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.relationships.PlaylistSong;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.sql.Timestamp;

@Projection(name = "PlaylistTrack", types = {Song.class, Artist.class, PlaylistSong.class, Album.class})
public interface PlaylistTrack {
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
  Timestamp getTimeAdded();
}
