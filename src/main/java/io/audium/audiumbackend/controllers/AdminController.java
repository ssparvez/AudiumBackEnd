package io.audium.audiumbackend.controllers;

import com.google.gson.JsonObject;
import com.sun.org.apache.regexp.internal.RE;
import io.audium.audiumbackend.entities.*;
import io.audium.audiumbackend.services.AdminService;
import io.audium.audiumbackend.services.LibraryService;
import io.audium.audiumbackend.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AdminController {

  @Autowired
  private VerificationService verificationService;
  @Autowired
  private AdminService        adminService;

  private LibraryService libraryService;

  public AdminController( LibraryService libraryService) {
    this.libraryService = libraryService;
  }
  /* Bcrypt hashing test function */
  @GetMapping(value = "/bcrypt/{rawData}")
  public ResponseEntity testBcrypt(@PathVariable String rawData) {
    return ResponseEntity.status(HttpStatus.OK).body(adminService.testBcrypt(rawData).toString());
  }

  /* AES encrypting test function */
  @GetMapping(value = "/aes/encrypt/{accountId}/{rawData}")
  public ResponseEntity testAesEncrypt(@PathVariable long accountId, @PathVariable String rawData) {
    String salt          = adminService.getAccountSalt(accountId);
    String encryptedData = verificationService.aesEncrypt(accountId, rawData).toString();
    System.out.println("AES encryption test:\n\taccountId=" + accountId + "\n\tRaw data=\"" + rawData + "\"\n\tSalt=\"" + salt + "\"\n\tEncrypted data=\"" + encryptedData + "\"");
    JsonObject response = new JsonObject();
    response.addProperty("accountId", accountId);
    response.addProperty("rawData", rawData);
    response.addProperty("salt", salt);
    response.addProperty("encryptedData", encryptedData);
    return ResponseEntity.status(HttpStatus.OK).body(response.toString());
  }

  /* AES decrypting test function */
  @GetMapping(value = "/aes/decrypt/{accountId}/{encryptedData}")
  public ResponseEntity testAesDecrypt(@PathVariable long accountId, @PathVariable String encryptedData) {
    String salt          = adminService.getAccountSalt(accountId);
    String decryptedData = verificationService.aesDecrypt(accountId, encryptedData).toString();
    System.out.println("AES decryption test:\n\taccountId=" + accountId + "\n\tRaw data=\"" + decryptedData + "\"\n\tSalt=\"" + salt + "\"\n\tEncrypted data=\"" + encryptedData + "\"");
    JsonObject response = new JsonObject();
    response.addProperty("accountId", accountId);
    response.addProperty("salt", salt);
    response.addProperty("encryptedData", encryptedData);
    response.addProperty("decryptedData", decryptedData);
    return ResponseEntity.status(HttpStatus.OK).body(response.toString());
  }

  //** ACCOUNT **//
  @CrossOrigin
  @DeleteMapping(value = "/admin/{adminId}/accounts/{accountId}/delete")
  public ResponseEntity deleteAccount(@RequestHeader(value = "Authorization") String token,
                                      @PathVariable long adminId,
                                      @PathVariable long accountId) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.deleteAccount(accountId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  @PutMapping(value="/admin/{adminId}/accounts/{accountId}/banstatus/{status}")
  public ResponseEntity banAccount(@RequestHeader(value = "Authorization") String token,
                                   @PathVariable long adminId,
                                   @PathVariable long accountId,
                                   @PathVariable boolean status) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.changeBanStatus(accountId, status)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }


  //** SONG **//

  @PostMapping(value = "/admin/{adminId}/song/add")
  public ResponseEntity addSong(@RequestHeader(value = "Authorization") String token,
                                @PathVariable long adminId,
                                @RequestBody Song song) {
    System.out.println(song.getSongId());
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if ( adminService.addSong(song)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  @PutMapping(value = "/songs/{songId}")
  public void updateSong(@PathVariable long songId, @RequestBody Song song) {
    adminService.updateSong(songId, song);
  }

  @CrossOrigin
  @DeleteMapping(value = "/admin/{adminId}/song/{songId}/delete")
  public ResponseEntity removeSong(@RequestHeader(value = "Authorization") String token,
                                   @PathVariable long adminId,
                                   @PathVariable long songId) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.removeSong(songId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

//** PLAYLIST **//

  @PostMapping(value = "/admin/{adminId}/playlist/add")
  public ResponseEntity createNewPlaylist(@RequestHeader(value = "Authorization") String token,
                                          @PathVariable long adminId,
                                          @RequestBody Playlist playlist) {
    System.out.println(playlist.getCreator().getAccountId());
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if ((libraryService.createNewPlaylist(playlist)) != null) {
        return ResponseEntity.status(HttpStatus.OK).body(playlist.getPlaylistId());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
      }
    }
    else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  @CrossOrigin
  @DeleteMapping(value = "/admin/{adminId}/playlist/{playlistId}/delete")
  public ResponseEntity deletePlaylist(@RequestHeader(value = "Authorization") String token,
                                       @PathVariable long adminId,
                                       @PathVariable long playlistId) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.deletePlaylist(playlistId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @PostMapping(value="/admin/{adminId}/playlist/{playlistId}/add/song/{songId}")
  public ResponseEntity addSongToPlaylist(@RequestHeader(value = "Authorization") String token,
                                          @PathVariable long adminId,
                                          @PathVariable long playlistId,
                                          @PathVariable long songId) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {

      if ( libraryService.addSongToPlaylist(playlistId, songId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  //** ALBUM **//

  @CrossOrigin
  @DeleteMapping(value = "/admin/{adminId}/album/{albumId}/delete")
  public ResponseEntity deleteAlbum(@RequestHeader(value = "Authorization") String token,
                                       @PathVariable long adminId,
                                       @PathVariable long albumId) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.deleteAlbum(albumId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }

  @PostMapping(value = "/admin/{adminId}/album/add")
  public ResponseEntity addAlbum(@RequestHeader(value = "Authorization") String token,
                                @PathVariable long adminId,
                                @RequestBody Album album) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if ( adminService.addAlbum(album)) {
        return ResponseEntity.status(HttpStatus.OK).body(album.getAlbumId());
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  @PostMapping(value="/admin/{adminId}/album/{albumId}/add/song/{songId}")
  public ResponseEntity addSongToAlbum(@RequestHeader(value = "Authorization") String token,
                                          @PathVariable long adminId,
                                          @PathVariable long albumId,
                                          @PathVariable long songId) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {

      if ( libraryService.addSongToAlbum(albumId, songId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
    else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
  }



  //** ARTIST **//

  @CrossOrigin
  @DeleteMapping(value = "/admin/{adminId}/artist/{artistId}/delete")
  public ResponseEntity deleteArtist(@RequestHeader(value = "Authorization") String token,
                                      @PathVariable long adminId,
                                      @PathVariable long artistId) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.deleteArtist(artistId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  @PutMapping(value = "/admin/{adminId}/artist/{artistId}/update")
  public ResponseEntity updateArtist(@RequestHeader(value = "Authorization") String token,
                                     @PathVariable long adminId,
                                     @PathVariable long artistId,
                                     @RequestBody Artist artist) {
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.updateArtist(artist, artistId)) {
        return ResponseEntity.status(HttpStatus.OK).body(true);
      } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }

  @PostMapping(value = "/admin/{adminId}/artists/label/{labelId}/create")
  public ResponseEntity addArtist(@RequestHeader(value = "Authorization") String token,
                                  @PathVariable long adminId,
                                  @PathVariable long labelId,
                                  @RequestBody Artist artist) {
    artist.setLabel(new Label());
    artist.getLabel().setAccountId(labelId);
    System.out.println(artist.getLabel().getAccountId());
    if (verificationService.verifyIntegrityAdminAccount(token, adminId) != null) {
      if (adminService.addArtist(artist, labelId)) {
        return ResponseEntity.status(HttpStatus.OK).body(artist.getArtistId());
      }
      else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);


    } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);

  }
}
