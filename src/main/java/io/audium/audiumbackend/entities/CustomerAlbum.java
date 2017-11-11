package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_album")
public class CustomerAlbum {
  @Id
  private Long accountId;
  private Long albumid;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getAlbumid() {
    return albumid;
  }

  public void setAlbumid(Long albumid) {
    this.albumid = albumid;
  }
}
