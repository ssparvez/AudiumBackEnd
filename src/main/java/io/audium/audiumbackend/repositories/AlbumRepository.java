package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, String> {

    @Query("select A from CustomerAlbum  C, Album A where C.accountid = ?1 and A.albumid = C.albumid")
    public List<Album> findCustomerAlbums(long accountid);
}
