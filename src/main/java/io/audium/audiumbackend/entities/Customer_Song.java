package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer_Song {
  @Id
  private Long songid;
  private java.sql.Timestamp timeadded;
  private Long accountid;

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

  public Long getAccountid() {
    return accountid;
  }

  public void setAccountid(Long accountid) {
    this.accountid = accountid;
  }
}
