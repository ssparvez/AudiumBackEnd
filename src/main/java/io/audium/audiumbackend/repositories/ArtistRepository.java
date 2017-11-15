package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.projections.LibraryArtist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, String> {

    @Transactional(readOnly = true)
    @Query("SELECT LibA.artistId AS artistId, LibA.artistName AS artistName FROM Customer C JOIN C.artists LibA WHERE C.accountId = ?1")
    public List<LibraryArtist> findFollowerByAccountId(long accountId);
}
