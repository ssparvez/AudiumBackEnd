package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_song")
public class CustomerSong {
  @Id
  private Long songId;
  private java.sql.Timestamp timeAdded;
  private Long accountId;

  public Long getSongId() {
    return songId;
  }

  public void setSongId(Long songId) {
    this.songId = songId;
  }

  public java.sql.Timestamp getTimeAdded() {
    return timeAdded;
  }

  public void setTimeAdded(java.sql.Timestamp timeAdded) {
    this.timeAdded = timeAdded;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
}
