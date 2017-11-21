package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMapping(
  name = "SearchArtistMapping",
  classes = {@ConstructorResult(targetClass = Artist.class, columns = {
    @ColumnResult(name = "artistId"),
    @ColumnResult(name = "artistName"),
    @ColumnResult(name = "bio")
  })
  }
)
@Entity
public class Artist {

  @Id
  private Long   artistId;
  private Long   accountId;
  @Column(name = "name")
  private String artistName;
  private String bio = "";

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "labelId", referencedColumnName = "accountId")
  private Label label;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist")
  private List<Album> albums;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
  @JsonIgnore
  private List<Song> songs;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
  @JsonIgnore
  private List<Customer> followers;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
  private List<Event> events;

  public Artist() {
  }

  public Artist(Integer artistId, String artistName, String bio) {
    this.artistId = artistId.longValue();
    this.artistName = artistName;
    this.bio = bio;
  }

  public Artist(Long artistId, String artistName) {
    this.artistId = artistId;
    this.artistName = artistName;
  }

  public Artist(Long artistId, Long accountId, String artistName, String bio) {
    this.artistId = artistId;
    this.accountId = accountId;
    this.artistName = artistName;
    this.bio = bio;
  }

  public Long getArtistId() {
    return artistId;
  }
  public void setArtistId(Long artistId) {
    this.artistId = artistId;
  }

  public Label getLabel() {
    return label;
  }
  public void setLabel(Label label) {
    this.label = label;
  }

  public String getArtistName() {
    return artistName;
  }
  public void setArtistName(String artistName) {
    artistName = artistName.replace(", ", " & ");
    this.artistName = artistName;
  }

  public String getBio() {
    return bio;
  }
  public void setBio(String bio) {
    this.bio = bio;
  }

  public List<Song> getSongs() {
    return this.songs;
  }
  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }

  public Long getAccountId() {
    return accountId;
  }
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public List<Album> getAlbums() {
    return albums;
  }
  public void setAlbums(List<Album> albums) {
    this.albums = albums;
  }

  public List<Event> getEvents() {
    return events;
  }
  public void setEvents(List<Event> events) {
    this.events = events;
  }
  public List<Customer> getFollowers() {
    return followers;
  }
}
