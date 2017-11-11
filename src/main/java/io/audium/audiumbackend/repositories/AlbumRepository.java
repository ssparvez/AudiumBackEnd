package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, String> {

    @Query("SELECT A FROM CustomerAlbum  CA, Album A WHERE CA.accountId = ?1 AND A.albumId = CA.albumId")
    public List<Album> findCustomerAlbums(long accountId);
}
