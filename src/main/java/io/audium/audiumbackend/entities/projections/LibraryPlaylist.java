package io.audium.audiumbackend.entities.projections;

import io.audium.audiumbackend.entities.Playlist;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "LibraryPlaylist", types = {Playlist.class})
public interface LibraryPlaylist {
    Long getPlaylistId();
    Long getDescription();
    String getName();
}
