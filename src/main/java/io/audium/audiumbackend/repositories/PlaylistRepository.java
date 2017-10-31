package io.audium.audiumbackend.repositories;


import io.audium.audiumbackend.entities.Playlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<Playlist, String> {


}