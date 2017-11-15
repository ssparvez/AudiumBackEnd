package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.projections.LibraryArtist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, String> {

    @Transactional(readOnly = true)
    @Query("SELECT LibA.artistId AS artistId, LibA.artistName AS artistName, LibA.bio AS bio FROM Customer C JOIN C.artists LibA WHERE C.accountId = ?1 ORDER BY artistName ASC")
    public List<LibraryArtist> findByFollowerAccountId(long accountId);

    @Transactional(readOnly = true)
    @Query("SELECT A.artistId AS artistId, A.artistName AS artistName, A.bio AS bio FROM Artist A WHERE A.artistId = ?1")
    public LibraryArtist findByArtistId(long artistId);
}
