package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, String> {

  @Query("select A from ArtistFollower C, Artist A where C.accountId = ?1 and A.artistid = C.artistid")
  public List<Artist> findCustomerArtists(long accountId);
}
