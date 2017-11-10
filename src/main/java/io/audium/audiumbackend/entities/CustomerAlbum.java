package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer_album")
public class CustomerAlbum {
  @Id
  private Long accountid;
  private Long albumid;

  public Long getAccountid() {
    return accountid;
  }

  public void setAccountid(Long accountid) {
    this.accountid = accountid;
  }

  public Long getAlbumid() {
    return albumid;
  }

  public void setAlbumid(Long albumid) {
    this.albumid = albumid;
  }
}
