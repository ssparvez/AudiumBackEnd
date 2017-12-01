package io.audium.audiumbackend.entities;

import javax.persistence.*;

@SqlResultSetMapping(
  name = "SearchGenreMapping",
  classes = {@ConstructorResult(targetClass = Genre.class, columns = {
    @ColumnResult(name = "genreId"),
    @ColumnResult(name = "genreName")
  })
  }
)
@Entity
public class Genre {

  @Id
  @Column(name = "genreId")
  private Long   genreId;
  @Column(name = "name")
  private String genreName;

  public Genre() {
  }

  public Genre(Integer genreId, String genreName) {
    this.genreId = genreId.longValue();
    this.genreName = genreName;
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
