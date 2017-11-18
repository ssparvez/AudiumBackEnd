package io.audium.audiumbackend.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long    accountId;
  private String  email;
  private String  passwordHash;
  private String  firstName;
  private String  lastName;
  private String  role;
  private boolean isActive;
  private String  username;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accountId")
  private UserPreferences userPreferences;

  public Account() {
  }

  public Account(Long accountId, String email, String passwordHash, String firstName, String lastName, String role, boolean isActive, String username) {
    this.accountId = accountId;
    this.email = email;
    this.passwordHash = passwordHash;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.isActive = isActive;
    this.username = username;
  }

  public Long getAccountId() {
    return accountId;
  }
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }

  public boolean getIsActive() {
    return isActive;
  }
  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public UserPreferences getUserPreferences() {
    return userPreferences;
  }
  public void setUserPreferences(UserPreferences userPreferences) {
    this.userPreferences = userPreferences;
  }
}
