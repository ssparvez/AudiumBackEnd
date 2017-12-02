package io.audium.audiumbackend.services;

import com.google.gson.JsonObject;
import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.repositories.*;
import io.audium.audiumbackend.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  @Autowired
  private AuthenticationRepository authenticationRepository;

  private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

 private  AccountRepository accountRepo;
 private SongRepository songRepository;
 private PlaylistRepository playlistRepo;
 private AlbumRepository albumRepo;
 private ArtistRepository artistRepo;

  public AdminService( AccountRepository accountRepo, SongRepository songRepository,PlaylistRepository playlistRepo,
                       AlbumRepository albumRepo, ArtistRepository artistRepo) {
    this.accountRepo = accountRepo;
    this.songRepository = songRepository;
    this.playlistRepo = playlistRepo;
    this.albumRepo = albumRepo;
    this.artistRepo = artistRepo;
  }

  /* Testing function: Returns Bcrypt hash of a string */
  public JsonObject testBcrypt(String rawData) {
    String hash = bcryptEncoder.encode(rawData);
    System.out.println("Bcrypt test:\n\tRaw data=\"" + rawData + "\"\n\tHash=\"" + hash + "\"");
    JsonObject jsonHash = new JsonObject();
    jsonHash.addProperty("rawData", rawData);
    jsonHash.addProperty("hash", hash);
    return jsonHash;
  }

  /* Testing function: Returns unique salt of the specified account */
  public String getAccountSalt(long accountId) {
    Object[] salt = authenticationRepository.findSaltByAccountId(accountId);
    if (salt != null) {
      return salt[0].toString();
    } else {
      return null;
    }
  }

  public boolean deleteAccount(long accountId) {
    return (accountRepo.deleteById(accountId) == 1);
  }

  //** SONG **//

  public boolean addSong(Song song) {

    if ( artistRepo.exists(new Long(song.getArtistId()))) {
      if ( songRepository.save(song) != null) {
        songRepository.linkSongToArtist(song.getArtistId(), song.getSongId());
        return true;
      }
      else return false;
    }
    else return false;

  }

  public void updateSong(Long songId, Song song) {
    songRepository.save(song);
  }

  public boolean removeSong(long songId) {
    return (songRepository.deleteById(songId) == 1);
  }

  //** PLAYLIST **//

  public boolean deletePlaylist(long playlistId) {
    return (playlistRepo.deletePlaylistById(playlistId) == 1);
  }


  //** ALBUM **/

  public boolean deleteAlbum(long albumId) {
    return (albumRepo.deleteById(albumId) == 1);
  }

  public boolean addAlbum(Album album) {
    if ( artistRepo.exists(album.getArtist().getArtistId())) {
      return ( albumRepo.save(album) != null);
    }
    else return false;
  }

  //** ARTIST **/

  public boolean deleteArtist( long artistId) {
    return ( artistRepo.deleteById(artistId) == 1);
  }

  public boolean addArtist(Artist artist, long labelId) {
    return ( artistRepo.save(artist) != null );
  }

}
