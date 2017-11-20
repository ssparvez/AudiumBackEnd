package io.audium.audiumbackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Genre {

  @Id
  @Column(name = "genreId")
  private Long   genreId;
  @Column(name = "name")
  private String genreName;

  public Genre() {
  }

  public Genre(Long genreId, String genreName) {
    this.genreId = genreId;
    this.genreName = genreName;
  }

  public Long getGenreId() {
    return genreId;
  }
  public void setGenreId(Long genreId) {
    this.genreId = genreId;
  }

  public String getGenreName() {
    return genreName;
  }
  public void setGenreName(String genreName) {
    this.genreName = genreName;
  }
}
