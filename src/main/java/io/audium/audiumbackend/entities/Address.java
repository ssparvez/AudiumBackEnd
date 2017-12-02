package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.relationships.ZipCodeState;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long   addressId;
  private String addressLine1;
  private String city;

  @ManyToOne
  @JoinColumn(name = "zipCode")
  @JsonIgnore
  private ZipCodeState zipCodeState;

  @Formula("zipCode")
  private Integer zipCode;

  @Formula("(SELECT ZS.state FROM zipcode_state AS ZS WHERE ZS.zipCode = zipCode)")
  private String state;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
  @JsonIgnore
  private List<Event> events;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
  @JsonIgnore
  private List<Partner> partners;

  public Address() {
  }

  public Address(Long addressId, String addressLine1, String city, ZipCodeState zipCodeState) {
    this.addressId = addressId;
    this.addressLine1 = addressLine1;
    this.city = city;
    this.zipCodeState = zipCodeState;
  }

  public Address(Long addressId, String addressLine1, String city, Integer zipCode, String state) {
    this.addressId = addressId;
    this.addressLine1 = addressLine1;
    this.city = city;
    this.zipCodeState = new ZipCodeState(zipCode, state);
  }

  public Long getAddressId() {
    return addressId;
  }
  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }
  public String getAddressLine1() {
    return addressLine1;
  }
  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public ZipCodeState getZipCodeState() {
    return zipCodeState;
  }
  public void setZipCodeState(ZipCodeState zipCodeState) {
    this.zipCodeState = zipCodeState;
  }
  public void setZipCodeState(Integer zipCode, String state) {
    this.zipCodeState = new ZipCodeState(zipCode, state);
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
  public List<Event> getEvents() {
    return events;
  }
}
