package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_album")
public class CustomerAlbum {
  @Id
  private Long accountId;
  private Long albumId;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(Long albumId) {
    this.albumId = albumId;
  }
}
