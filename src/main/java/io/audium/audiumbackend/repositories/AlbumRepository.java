package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, String> {

    @Query("select A from Album A, Album_Song ASO, Customer_Song  CS, Song S where CS.accountid = ?1 and S.songid = CS.songid and S.songid = ASO.songid")
    public List<Album> findCustomerAlbums(long accountid);
}
