package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.relationships.SongPlay;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

@Projection(name = "PopularTrack", types = {Song.class, Artist.class, SongPlay.class, Album.class})
public interface PopularTrack {
    Long getSongId();
    String getTitle();
    Long getArtistId();
    String getArtistName();
    //List<Artist> getArtists();
    Long getAlbumId();
    String getAlbumTitle();
    Time getDuration();
    boolean getIsExplicit();
    int getPlayCount();
}
