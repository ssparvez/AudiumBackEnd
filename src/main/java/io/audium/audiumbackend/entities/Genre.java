package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Genre {
  @Id
  private Long genreid;
  private String name;

  public Long getGenreid() {
    return genreid;
  }

  public void setGenreid(Long genreid) {
    this.genreid = genreid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
