package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
  private Long accountid;
  private String email;
  private String password;
  private String firstname;
  private String lastname;
  private String role;
  private Long isactive;
  private String username;

    public Account() {
    }

    public Account(Long accountid, String email, String password, String firstname, String lastname, String role, Long isactive, String username) {
        this.accountid = accountid;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.isactive = isactive;
        this.username = username;
    }

    public Long getAccountid() {
    return accountid;
  }

  public void setAccountid(Long accountid) {
    this.accountid = accountid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Long getIsactive() {
    return isactive;
  }

  public void setIsactive(Long isactive) {
    this.isactive = isactive;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
