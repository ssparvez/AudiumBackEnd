package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Advertiser")
@PrimaryKeyJoinColumn(name = "accountId", referencedColumnName = "accountId")
public class Advertiser extends Partner {

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "advertiser")
  @JsonIgnore
  private List<Advertisement> advertisements;

  public Advertiser() {
  }

  public List<Advertisement> getAdvertisements() {
    return advertisements;
  }
}
