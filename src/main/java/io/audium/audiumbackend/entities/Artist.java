package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Artist {
  @Id
  private Long artistid;
  private Long labelid;
  private Long accountId;
  private String name;
  private String bio;

  @OneToMany(mappedBy = "artist")
  private List<Album> albums;

  public Artist() {
  }

  public Long getArtistid() {
    return artistid;
  }

  public void setArtistid(Long artistid) {
    this.artistid = artistid;
  }

  public Long getLabelid() {
    return labelid;
  }

  public void setLabelid(Long labelid) {
    this.labelid = labelid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
}
