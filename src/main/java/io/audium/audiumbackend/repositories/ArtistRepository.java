package io.audium.audiumbackend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import io.audium.audiumbackend.entities.Artist;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, String> {

    @Query("select A from ArtistFollower C, Artist A where C.accountid = ?1 and A.artistid = C.artistid")
    public List<Artist> findCustomerArtists(long accountid);
}
