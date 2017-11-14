package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "LibraryAlbum", types = {Album.class})
public interface LibraryAlbum {
    Long getAlbumId();
    String getTitle();
    String getYear();
    Artist getArtist();
}
