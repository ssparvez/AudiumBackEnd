package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, String> {

    @Query("SELECT A FROM ArtistFollower AF, Artist A WHERE AF.accountId = ?1 AND A.artistId = AF.artistId")
    public List<Artist> findCustomerArtists(long accountId);
}
