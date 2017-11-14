package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.projections.LibraryArtist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(excerptProjection = LibraryArtist.class)
public interface ArtistRepository extends CrudRepository<Artist, String> {

    @Transactional(readOnly = true)
    @Query("SELECT LibA.artistId AS artistId, LibA.name AS name FROM Customer C JOIN C.artists LibA WHERE C.accountId = ?1")
    public List<LibraryArtist> findFollowerByAccountId(long accountId);
}
