package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artist_follower")
public class ArtistFollower {
  @Id
  private Long artistid;
  private Long accountid;

  public Long getArtistid() {
    return artistid;
  }

  public void setArtistid(Long artistid) {
    this.artistid = artistid;
  }

  public Long getAccountid() {
    return accountid;
  }

  public void setAccountid(Long accountid) {
    this.accountid = accountid;
  }
}
