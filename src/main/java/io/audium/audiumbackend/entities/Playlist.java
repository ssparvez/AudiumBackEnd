package io.audium.audiumbackend.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Playlist {
  @Id
  private Long playlistId;
  private Long accountId;
  private String name;
  private String description;
  private Long isPublic;

  @ManyToMany
  @JoinTable(
    name = "playlist_song",
    joinColumns = @JoinColumn(name = "playlistId", referencedColumnName = "playlistId"),
    inverseJoinColumns = @JoinColumn(name = "songId", referencedColumnName = "songId"))
  private List<Song> songs;


  public Playlist() {
  }

  public Playlist(Long playlistId, Long accountId, String name, String description, Long isPublic) {
    this.playlistId = playlistId;
    this.accountId = accountId;
    this.name = name;
    this.description = description;
    this.isPublic = isPublic;
  }


  public Long getPlaylistId() {
    return playlistId;
  }

  public void setPlaylistId(Long playlistId) {
    this.playlistId = playlistId;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(Long isPublic) {
    this.isPublic = isPublic;
  }

  public List<Song> getSongs() {
    return songs;
  }

  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }
}
