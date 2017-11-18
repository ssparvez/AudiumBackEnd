package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.relationships.CustomerSong;
import io.audium.audiumbackend.entities.relationships.SongPlay;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Customer")
@PrimaryKeyJoinColumn(name = "accountId", referencedColumnName = "accountId")
public class Customer extends Account {

  private Date   dateOfBirth;
  private String gender;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
  private List<CustomerSong> customerSongs;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
    name = "customer_album",
    joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"),
    inverseJoinColumns = @JoinColumn(name = "albumId", referencedColumnName = "albumId"))
  private List<Album> albums;

  @OneToMany(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "accountId")
  private List<Playlist> createdPlaylists;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
    name = "playlist_follower",
    joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"),
    inverseJoinColumns = @JoinColumn(name = "playlistId", referencedColumnName = "playlistId"))
  private List<Playlist> playlists;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
    name = "artist_follower",
    joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"),
    inverseJoinColumns = @JoinColumn(name = "artistId", referencedColumnName = "artistId"))
  private List<Artist> artists;

  @ManyToMany(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinTable(
    name = "customer_follower",
    joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"),
    inverseJoinColumns = @JoinColumn(name = "followerId", referencedColumnName = "accountId"))
  private List<Customer> followers;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
  @JsonIgnore
  private List<Customer> following;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
  @JsonIgnore
  private List<SongPlay> songPlays;

  public Customer() {
  }

  public Customer(Date dateOfBirth, String gender) {
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  //public List<Song> getSongs() { return songs; }
  //public void setSongs(List<Song> songs) { this.songs = songs; }

  public List<Playlist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(List<Album> albums) {
    this.albums = albums;
  }

  public List<CustomerSong> getCustomerSongs() {
    return customerSongs;
  }

  public void setCustomerSongs(List<CustomerSong> customerSongs) {
    this.customerSongs = customerSongs;
  }

  public List<Playlist> getCreatedPlaylists() {
    return createdPlaylists;
  }

  public void setCreatedPlaylists(List<Playlist> createdPlaylists) {
    this.createdPlaylists = createdPlaylists;
  }

  public List<SongPlay> getSongPlays() {
    return songPlays;
  }

  public void setSongPlays(List<SongPlay> songPlays) {
    this.songPlays = songPlays;
  }
  public List<Customer> getFollowers() {
    return followers;
  }
  public List<Customer> getFollowing() {
    return following;
  }
}
