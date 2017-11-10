package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="playlist_follower")
public class PlaylistFollower {
  @Id
  private Long playlistid;
  private Long accountid;

  public Long getPlaylistid() {
    return playlistid;
  }

  public void setPlaylistid(Long playlistid) {
    this.playlistid = playlistid;
  }

  public Long getAccountid() {
    return accountid;
  }

  public void setAccountid(Long accountid) {
    this.accountid = accountid;
  }
}
