package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "PaymentInfo")
public class PaymentInfo {

  @Id
  private Long   accountId;
  private Date   creditCardExpiration;
  private String creditCardHash;
  private Long   zipCode;

  public PaymentInfo() {
  }
  public PaymentInfo(Long accountId, Date creditCardExpiration, String creditCardHash, Long zipCode) {
    this.accountId = accountId;
    this.creditCardExpiration = creditCardExpiration;
    this.creditCardHash = creditCardHash;
    this.zipCode = zipCode;
  }

  public Long getAccountId() {
    return accountId;
  }
  public void setAccounId(Long accountId) {
    this.accountId = accountId;
  }

  public Date getCreditCardExpiration() {
    return creditCardExpiration;
  }
  public void setCreditCardExpiration(Date creditCardExpiration) {
    this.creditCardExpiration = creditCardExpiration;
  }

  public String getCreditCardHash() {
    return creditCardHash;
  }
  public void setCreditCardHash(String creditCardHash) {
    this.creditCardHash = creditCardHash;
  }

  public Long getZipCode() {
    return zipCode;
  }
  public void setZipCode(Long zipCode) {
    this.zipCode = zipCode;
  }
}
