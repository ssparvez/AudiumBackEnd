package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Label")
@PrimaryKeyJoinColumn(name = "accountId", referencedColumnName = "accountId")
public class Label extends Partner {

  private String info = "";

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "label")
  @JsonIgnore
  private List<Artist> artists;

  public Label() {
  }

  public Label(String info) {
    this.info = info;
  }

  public String getInfo() {
    return info;
  }
  public void setInfo(String info) {
    this.info = info;
  }
  public List<Artist> getArtists() {
    return artists;
  }
}
