package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.projections.LibraryAlbum;
import io.audium.audiumbackend.repositories.custom.AlbumRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long>, AlbumRepositoryCustom {

  @Transactional(readOnly = true)
  @Query("SELECT LibA.albumId AS albumId, LibA.albumTitle AS albumTitle, LibA.releaseYear AS releaseYear, LibA.artist.artistId AS artistId, LibA.artist.artistName AS artistName FROM Customer C JOIN C.albums LibA WHERE C.accountId = ?1  ORDER BY albumTitle ASC")
  public List<LibraryAlbum> findCustomerAlbums(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT LibA.albumId AS albumId, LibA.albumTitle AS albumTitle, LibA.releaseYear AS releaseYear, LibA.artist.artistId AS artistId, LibA.artist.artistName AS artistName FROM Artist A JOIN A.albums LibA WHERE A.artistId = ?1 ORDER BY year DESC")
  public List<LibraryAlbum> findArtistAlbums(long artistId);

  @Transactional(readOnly = true)
  @Query("SELECT A.albumId AS albumId, A.albumTitle AS albumTitle, A.releaseYear AS releaseYear, A.artist.artistId AS artistId, A.artist.artistName AS artistName FROM Album A WHERE A.albumId = ?1")
  public LibraryAlbum findByAlbumId(long albumId);

  @Transactional(readOnly = true)
  @Query(value= "SELECT A.albumId FROM Customer_Album A WHERE A.accountId = ?1", nativeQuery = true)
  public  List<Long> getListOfSavedAlbumIds(long accountId);

  @Transactional
  @Modifying
  @Query(value="INSERT INTO Customer_Album VALUES(?1,?2)", nativeQuery = true)
  public int saveAlbum(long accountId, long albumId);

  @Transactional
  @Modifying
  @Query(value="DELETE FROM Customer_Album WHERE albumId = ?1 AND accountId = ?2", nativeQuery = true)
  public int removeAlbum(long albumId, long accountId);

  @Transactional
  @Modifying
  @Query("DELETE FROM Album A WHERE A.albumId = ?1")
  public int deleteById(long albumId);

  @Transactional
  @Modifying
  @Query(value="INSERT INTO Album_Song VALUES(?1,?2, 0)", nativeQuery = true)
  public int addSongToAlbum(long albumId, long accountId);

}
