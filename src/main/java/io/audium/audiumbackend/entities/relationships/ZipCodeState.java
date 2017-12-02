package io.audium.audiumbackend.entities.relationships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.Address;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "zipcode_state")
public class ZipCodeState {

  @Id
  private Integer zipCode;
  private String  state;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "zipCodeState")
  @JsonIgnore
  private List<Address> addresses;

  public ZipCodeState() {
  }

  public ZipCodeState(Integer zipCode, String state) {
    this.zipCode = zipCode;
    this.state = state;
  }

  public Integer getZipCode() {
    return this.zipCode;
  }
  public void setZipCode(Integer zipCode) {
    this.zipCode = zipCode;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public List<Address> getAddresses() {
    return addresses;
  }
}
