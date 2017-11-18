package io.audium.audiumbackend.entities;

import javax.persistence.*;

@Entity
@Table(name = "Advertisement")
public class Advertisement {

  @Id
  private Long    advertisementId;
  private String  url;
  private boolean isActive;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accountId", referencedColumnName = "accountId")
  private Advertiser advertiser;

  public Advertisement() {
  }

  public Advertisement(Long advertisementId, String url, boolean isActive, Advertiser advertiser) {
    this.advertisementId = advertisementId;
    this.url = url;
    this.isActive = isActive;
    this.advertiser = advertiser;
  }
  public Long getAdvertisementId() {
    return advertisementId;
  }
  public void setAdvertisementId(Long advertisementId) {
    this.advertisementId = advertisementId;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public boolean isActive() {
    return isActive;
  }
  public void setActive(boolean active) {
    isActive = active;
  }
  public Advertiser getAdvertiser() {
    return advertiser;
  }
  public void setAdvertiser(Advertiser advertiser) {
    this.advertiser = advertiser;
  }
}
