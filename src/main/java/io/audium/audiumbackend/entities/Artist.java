package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Artist {

  @Id
  private Long   artistId;
  private Long   accountId;
  @Column(name = "name")
  private String artistName;
  private String bio;

  @ManyToOne(fetch = FetchType.LAZY)
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
  @JsonIgnore
  private List<Event> events;

  public Artist() {
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
  public List<Customer> getFollowers() {
    return followers;
  }
}
