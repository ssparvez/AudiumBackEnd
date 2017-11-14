package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Artist;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "LibraryArtist", types = {Artist.class})
public interface LibraryArtist {
    Long getArtistId();
    String getName();
}
