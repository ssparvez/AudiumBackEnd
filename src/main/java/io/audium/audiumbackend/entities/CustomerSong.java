package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_song")
public class CustomerSong {
  @Id
  private Long songid;
  private java.sql.Timestamp timeadded;
  private Long accountId;

  public Long getSongid() {
    return songid;
  }

  public void setSongid(Long songid) {
    this.songid = songid;
  }

  public java.sql.Timestamp getTimeadded() {
    return timeadded;
  }

  public void setTimeadded(java.sql.Timestamp timeadded) {
    this.timeadded = timeadded;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
}
