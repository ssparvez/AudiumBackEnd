package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artist_follower")
public class ArtistFollower {
  @Id
  private Long artistid;
  private Long accountId;

  public Long getArtistid() {
    return artistid;
  }

  public void setArtistid(Long artistid) {
    this.artistid = artistid;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
}
