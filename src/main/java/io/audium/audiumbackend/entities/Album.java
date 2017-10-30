package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Album {
  @Id
  private Long albumid;
  private String title;
  private String year;

  public Album() {
  }

  public Album(Long albumid, String title, String year) {
    this.albumid = albumid;
    this.title = title;
    this.year = year;
  }

  public Long getAlbumid() {
    return albumid;
  }

  public void setAlbumid(Long albumid) {
    this.albumid = albumid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }
}
