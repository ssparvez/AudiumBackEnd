package io.audium.audiumbackend.entities;

import javax.persistence.*;
import java.util.List;

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

  @Transient
  private List<Song> songs;

  @Transient
  private List<Artist> artists;

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
  public List<Song> getSongs() {
    return songs;
  }
  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }
  public List<Artist> getArtists() {
    return artists;
  }
  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }
}
