package io.audium.audiumbackend.entities.relationships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.Address;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

@Immutable
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
    return zipCode;
  }
  public String getState() {
    return state;
  }
  public List<Address> getAddresses() {
    return addresses;
  }
}
