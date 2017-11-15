package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.projections.LibraryAlbum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT LibA.albumId AS albumId, LibA.albumTitle AS title, LibA.year AS year, LibA.artist AS artist FROM Customer C JOIN C.albums LibA WHERE C.accountId = ?1")
    public List<LibraryAlbum> findCustomerAlbums(long accountId);
}
