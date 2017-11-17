package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.projections.LibraryAlbum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {

  @Transactional(readOnly = true)
  @Query("SELECT LibA.albumId AS albumId, LibA.albumTitle AS albumTitle, LibA.releaseYear AS releaseYear, LibA.artist.artistId AS artistId, LibA.artist.artistName AS artistName FROM Customer C JOIN C.albums LibA WHERE C.accountId = ?1  ORDER BY albumTitle ASC")
  public List<LibraryAlbum> findCustomerAlbums(long accountId);

  @Transactional(readOnly = true)
  @Query("SELECT LibA.albumId AS albumId, LibA.albumTitle AS albumTitle, LibA.releaseYear AS releaseYear, LibA.artist.artistId AS artistId, LibA.artist.artistName AS artistName FROM Artist A JOIN A.albums LibA WHERE A.artistId = ?1 ORDER BY year DESC")
  public List<LibraryAlbum> findArtistAlbums(long artistId);

  @Transactional(readOnly = true)
  @Query("SELECT A.albumId AS albumId, A.albumTitle AS albumTitle, A.releaseYear AS releaseYear, A.artist.artistId AS artistId, A.artist.artistName AS artistName FROM Album A WHERE A.albumId = ?1")
  public LibraryAlbum findByAlbumId(long albumId);
}
