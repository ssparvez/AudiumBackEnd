package io.audium.audiumbackend.entities;

import javax.persistence.*;

@Entity
@Table(name = "Partner")
@PrimaryKeyJoinColumn(name = "accountId", referencedColumnName = "accountId")
public class Partner extends Account {

  private String  company;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "addressId")
  private Address address;
  private boolean approved = true;

  public Partner() {
  }

  public Partner(String company, Address address, boolean approved) {
    this.company = company;
    this.address = address;
    this.approved = approved;
  }

  public String getCompany() {
    return company;
  }
  public void setCompany(String company) {
    this.company = company;
  }
  public Address getAddress() {
    return address;
  }
  public void setAddress(Address address) {
    this.address = address;
  }
  public boolean getIsApproved() {
    return approved;
  }
  public void setIsApproved(boolean approved) {
    this.approved = approved;
  }
}
