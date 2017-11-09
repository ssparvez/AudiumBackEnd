package io.audium.audiumbackend.repositories;


import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface PlaylistRepository extends CrudRepository<Playlist, String> {
    @Transactional
    public Playlist findByPlaylistid(long playlistid);

}