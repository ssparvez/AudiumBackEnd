package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.relationships.AlbumSong;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

@Projection(name = "AlbumTrack", types = {Song.class, Artist.class, AlbumSong.class, Album.class})
public interface AlbumTrack {
    Long getSongId();
    String getTitle();
    Long getArtistId();
    String getArtistName();
    //List<Artist> getArtists();
    Long getAlbumId();
    String getAlbumTitle();
    Time getDuration();
    boolean getIsExplicit();
    int getTrackNumber();
}
