package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Event;
import io.audium.audiumbackend.entities.projections.LibraryArtist;
import io.audium.audiumbackend.repositories.custom.ArtistRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Long>, ArtistRepositoryCustom {

  @Transactional(readOnly = true)
  @Query("SELECT LibA.artistId AS artistId, LibA.artistName AS artistName, LibA.bio AS bio FROM Customer C JOIN C.artists LibA WHERE C.accountId = ?1 ORDER BY artistName ASC")
  public List<LibraryArtist> findByFollowerAccountId(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT A.artistId AS artistId, A.artistName AS artistName, A.bio AS bio FROM Artist A WHERE A.artistId = ?1")
  public LibraryArtist findByArtistId(long artistId);

  @Transactional(readOnly = true)
  @Query("SELECT A.artistId AS artistId, A.artistName AS artistName, A.bio AS bio FROM Artist A WHERE A.accountId = ?1")
  public LibraryArtist findByAccountId(long accountId);

  @Query("SELECT E FROM Artist A INNER JOIN A.events AS E WHERE A.artistId = ?1 AND E.eventDate >= CURRENT_DATE() AND (E.isCancelled = FALSE) ORDER BY (ABS(E.eventDate - CURRENT_DATE()) - E.isCancelled) ASC")
  public List<Event> findArtistEvents(long artistId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO Artist_Follower VALUES(?1,?2)", nativeQuery = true)
  public int followArtist(long artistId, long accountId);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM Artist_Follower WHERE artistId = ?1 AND accountId = ?2", nativeQuery = true)
  public int unfollowArtist(long artistId, long accountId);

  @Transactional(readOnly = true)
  @Query(value = "SELECT A.artistId FROM Artist_Follower A WHERE A.accountId = ?1", nativeQuery = true)
  public List<Long> getListOfFollowedArtistIds(long accountId);

  @Transactional
  @Modifying
  @Query("DELETE FROM Artist A WHERE A.artistId = ?1")
  public int deleteById(long artistId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO Artist VALUES(?1, ?2, NULL, ?3, ?4)", nativeQuery = true)
  public int insertIntoArtist(long artistId, long labelId, String name, String bio);
}
