package io.audium.audiumbackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserPreferences")
public class UserPreferences {

  @Id
  private Long accountId;

  @Column(name = "language")
  private String  language             = "English";
  @Column(name = "publicProfile")
  private boolean publicProfile        = true;
  @Column(name = "defaultPublicSession")
  private boolean defaultPublicSession = true;
  @Column(name = "showExplicitContent")
  private boolean showExplicitContent  = true;
  @Column(name = "quality")
  private String  quality              = "192kbps";

  //@Formula("(SELECT A.accountId, A.email, A.passwordHash, A.firstName, A.lastName, A.role, A.isActive, A.username FROM Account AS A WHERE A.accountId = accountId)")
  //private Account account;

  //@OneToOne(fetch = FetchType.LAZY)
  //@JoinColumn(name = "accountId")
  //Account account;

  public UserPreferences() {
  }

  public UserPreferences(Long accountId, String language, boolean publicProfile, boolean defaultPublicSession, boolean showExplicitContent, String quality) {
    this.accountId = accountId;
    this.language = language;
    this.publicProfile = publicProfile;
    this.defaultPublicSession = defaultPublicSession;
    this.showExplicitContent = showExplicitContent;
    this.quality = quality;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public boolean getPublicProfile() {
    return publicProfile;
  }
  public void setPublicProfile(boolean publicProfile) {
    this.publicProfile = publicProfile;
  }
  public boolean getDefaultPublicSession() {
    return defaultPublicSession;
  }
  public void setDefaultPublicSession(boolean defaultPublicSession) {
    this.defaultPublicSession = defaultPublicSession;
  }
  public Long getAccountId() {
    return accountId;
  }
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
  public boolean getShowExplicitContent() {
    return showExplicitContent;
  }
  public void setShowExplicitContent(boolean showExplicitContent) {
    this.showExplicitContent = showExplicitContent;
  }
  public String getQuality() {
    return quality;
  }
  public void setQuality(String quality) {
    this.quality = quality;
  }

  /*public Account getAccount() {
    return account;
  }*/
}
