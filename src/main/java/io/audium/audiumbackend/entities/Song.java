package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.relationships.AlbumSong;
import io.audium.audiumbackend.entities.relationships.CustomerSong;
import io.audium.audiumbackend.entities.relationships.PlaylistSong;
import io.audium.audiumbackend.entities.relationships.SongPlay;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@SqlResultSetMapping(
  name = "SearchSongMapping",
  classes = {@ConstructorResult(targetClass = Song.class, columns = {
    @ColumnResult(name = "songId"),
    @ColumnResult(name = "title"),
    @ColumnResult(name = "duration"),
    @ColumnResult(name = "year"),
    @ColumnResult(name = "isExplicit"),
    @ColumnResult(name = "artistId"),
    @ColumnResult(name = "artistName"),
    @ColumnResult(name = "albumId"),
    @ColumnResult(name = "albumTitle"),
    @ColumnResult(name = "genreId"),
    @ColumnResult(name = "genreName")
  })
  }
)
@Entity
public class Song {

  @Id
  private Long songId;
  private String title = "Untitled";
  private Time    duration;
  @JsonIgnore
  private String  file;
  private Date    year;
  private boolean isExplicit;
  private String  lyrics;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genreId", referencedColumnName = "genreId")
  private Genre genre;

  @ManyToMany(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinTable(
    name = "artist_song",
    joinColumns = @JoinColumn(name = "songId", referencedColumnName = "songId"),
    inverseJoinColumns = @JoinColumn(name = "artistId", referencedColumnName = "artistId"))
  private List<Artist> artists;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
  @JsonIgnore
  private List<AlbumSong> albumSongs;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
  @JsonIgnore
  private List<PlaylistSong> playlistSongs;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
  @JsonIgnore
  private List<CustomerSong> customerSongs;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
  @JsonIgnore
  private List<SongPlay> songPlays;

  @Formula("(SELECT A.artistId FROM Song AS S JOIN artist_song AS ArtS ON S.songId = ArtS.songId JOIN Artist A ON ArtS.artistId = A.artistId WHERE ArtS.isPrimaryArtist AND ArtS.songId = songId GROUP BY S.songId LIMIT 1)")
  private Integer artistId;

  @Formula("(SELECT A.name FROM Song AS S JOIN artist_song AS ArtS ON S.songId = ArtS.songId JOIN Artist A ON ArtS.artistId = A.artistId WHERE ArtS.isPrimaryArtist AND ArtS.songId = songId GROUP BY S.songId LIMIT 1)")
  private String artistName;

  @Formula("(SELECT A.albumId FROM Song AS S JOIN album_song AS AlbS ON S.songId = AlbS.songId JOIN Album A ON AlbS.albumId = A.albumId WHERE AlbS.songId = songId GROUP BY S.songId LIMIT 1)")
  private Integer albumId;

  @Formula("(SELECT A.title FROM Song AS S JOIN album_song AS AlbS ON S.songId = AlbS.songId JOIN Album A ON AlbS.albumId = A.albumId WHERE AlbS.songId = songId GROUP BY S.songId LIMIT 1)")
  private String albumTitle;

  @JsonIgnore
  @Formula("(SELECT COUNT(SP.songId) FROM Song AS S JOIN song_play AS SP ON S.songId = SP.songId WHERE SP.songId = songId)")
  private int playCount;

  public Song() {
  }

  public Song(Integer songId, String title, java.util.Date duration, java.util.Date year, Boolean isExplicit, Integer artistId, String artistName, Integer albumId, String albumTitle, Integer genreId, String genreName) {
    this.songId = songId.longValue();
    this.title = title;
    this.duration = new Time(duration.getTime());
    this.year = new Date(year.getTime());
    this.isExplicit = isExplicit;
    this.genre = new Genre(genreId.longValue(), genreName);
    this.artistId = artistId;
    this.artistName = artistName;
    this.albumId = albumId;
    this.albumTitle = albumTitle;
  }

  public Song(Long songId, String title, Time duration, String file, Date year, Genre genre, boolean isExplicit, String lyrics) {
    this.songId = songId;
    this.title = title;
    System.out.println("Java.sql:\n  Duration: " + duration.toString() + "\n  Year: " + year.toString());
    this.duration = duration;
    this.file = file;
    this.year = year;
    this.genre = genre;
    this.isExplicit = isExplicit;
    this.lyrics = lyrics;
  }

  public Long getSongId() {
    return songId;
  }
  public void setSongId(Long songId) {
    this.songId = songId;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public Time getDuration() {
    return duration;
  }
  public void setDuration(Time duration) {
    this.duration = duration;
  }

  public String getFile() {
    return file;
  }
  public void setFile(String file) {
    this.file = file;
  }

  public Date getYear() {
    return year;
  }
  public void setYear(Date year) {
    this.year = year;
  }

  public boolean getIsExplicit() {
    return isExplicit;
  }
  public void setIsExplicit(boolean isExplicit) {
    this.isExplicit = isExplicit;
  }

  public String getLyrics() {
    return lyrics;
  }
  public void setLyrics(String lyrics) {
    this.lyrics = lyrics;
  }

  public List<Artist> getArtists() {
    return artists;
  }
  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }

  public Artist getArtist(Long artistId) {
    System.out.println(this.artists.get(0).getArtistName());
    return this.artists.get(0);
  }

  public List<AlbumSong> getAlbumSongs() {
    return albumSongs;
  }
  public void setAlbumSongs(List<AlbumSong> albumSongs) {
    this.albumSongs = albumSongs;
  }

  public List<CustomerSong> getCustomerSongs() {
    return customerSongs;
  }
  public void setCustomerSongs(List<CustomerSong> customerSongs) {
    this.customerSongs = customerSongs;
  }
  public List<PlaylistSong> getPlaylistSongs() {
    return playlistSongs;
  }
  public void setPlaylistSongs(List<PlaylistSong> playlistSongs) {
    this.playlistSongs = playlistSongs;
  }
  public List<SongPlay> getSongPlays() {
    return songPlays;
  }
  public void setSongPlays(List<SongPlay> songPlays) {
    this.songPlays = songPlays;
  }
  public int getPlayCount() {
    return playCount;
  }
  public void setPlayCount(int playCount) {
    this.playCount = playCount;
  }
  public Genre getGenre() {
    return genre;
  }
  public void setGenre(Genre genre) {
    this.genre = genre;
  }
  public Integer getArtistId() {
    return artistId;
  }
  public void setArtistId(Integer artistId) {
    this.artistId = artistId;
  }
  public Integer getAlbumId() {
    return albumId;
  }
  public void setAlbumId(Integer albumId) {
    this.albumId = albumId;
  }
  public String getArtistName() {
    return artistName;
  }
  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }
  public String getAlbumTitle() {
    return albumTitle;
  }
  public void setAlbumTitle(String albumTitle) {
    this.albumTitle = albumTitle;
  }
}
