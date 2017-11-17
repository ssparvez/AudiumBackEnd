package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.relationships.AlbumSong;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Album {

  @Id
  private Long   albumId;
  @Column(name = "title")
  private String albumTitle;
  @Column(name = "year")
  private Date   releaseYear;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "artistId"
  )
  private Artist artist;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
  @JsonIgnore
  private List<AlbumSong> albumSongs;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "albums")
  @JsonIgnore
  private List<Customer> followers;

  public Album() {
  }

  public Album(Long albumId, String title, Date releaseYear) {
    this.albumId = albumId;
    this.albumTitle = title;
    this.releaseYear = releaseYear;
  }

  public Long getAlbumId() {
    return albumId;
  }
  public void setAlbumId(Long albumId) {
    this.albumId = albumId;
  }

  public String getAlbumTitle() {
    return albumTitle;
  }
  public void setAlbumTitle(String albumTitle) {
    this.albumTitle = albumTitle;
  }

  public Date getReleaseYear() {
    return releaseYear;
  }
  public void setReleaseYear(Date releaseYear) {
    this.releaseYear = releaseYear;
  }

  public Artist getArtist() {
    return artist;
  }
  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  public List<AlbumSong> getAlbumSongs() {
    return albumSongs;
  }
  public void setAlbumSongs(List<AlbumSong> albumSongs) {
    this.albumSongs = albumSongs;
  }
}
