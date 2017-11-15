package io.audium.audiumbackend.repositories;

import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.LibrarySong;
import io.audium.audiumbackend.entities.projections.PlaylistTrack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, String> {

    @Transactional(readOnly = true)
    public Song findBySongId(long songId);

    @Transactional(readOnly = true)
    //@Query("SELECT CS.song.songId AS songId, CS.song.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, Alb.albumId AS albumId, Alb.albumTitle AS albumTitle, CS.timeAdded AS timeAdded FROM Customer C INNER JOIN C.customerSongs CS INNER JOIN CS.song.artists Art INNER JOIN CS.song.albumSongs AlbSo INNER JOIN AlbSo.album Alb WHERE C.accountId = ?1 GROUP BY CS.song")
    @Query("SELECT CS.song.songId AS songId, CS.song.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, Alb.albumId AS albumId, Alb.albumTitle AS albumTitle, CS.song.file AS file, CS.timeAdded AS timeAdded FROM Customer C INNER JOIN C.customerSongs CS INNER JOIN CS.song.artists Art INNER JOIN CS.song.albumSongs AlbSo INNER JOIN AlbSo.album Alb WHERE C.accountId = ?1 GROUP BY CS.song")
    public List<LibrarySong> findCustomerSongs(long accountId);

    @Transactional(readOnly = true)
    @Query("SELECT PS.song.songId AS songId, PS.song.title AS title, Art.artistId AS artistId, Art.artistName AS artistName, Alb.albumId AS albumId, Alb.albumTitle AS albumTitle, PS.timeAdded AS timeAdded FROM PlaylistSong PS INNER JOIN PS.song.artists Art INNER JOIN PS.song.albumSongs AlbSo INNER JOIN AlbSo.album Alb WHERE PS.playlist.playlistId = ?1 GROUP BY PS.song")
    public List<PlaylistTrack> findPlaylistSongs(long playlistId);
}
