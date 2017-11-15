package io.audium.audiumbackend.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="paymentinfo")
public class PaymentInfo {

    @Id
  private Long accountId;
  private java.sql.Date creditcardexpire;
  private String creditcardhash;
  private Long zipCode;

    public PaymentInfo() {
    }
    public PaymentInfo(Long accountId, Date creditcardexpire, String creditcardhash, Long zipCode) {
        this.accountId = accountId;
        this.creditcardexpire = creditcardexpire;
        this.creditcardhash = creditcardhash;
        this.zipCode = zipCode;
    }

    public Long getAccountId() {
    return accountId;
  }

    public void setAccounId(Long accountId) {
    this.accountId = accountId;
  }

  public java.sql.Date getCreditcardexpire() {
    return creditcardexpire;
  }

  public void setCreditcardexpire(java.sql.Date creditcardexpire) {
    this.creditcardexpire = creditcardexpire;
  }

  public String getCreditcardhash() {
    return creditcardhash;
  }

  public void setCreditcardhash(String creditcardhash) {
    this.creditcardhash = creditcardhash;
  }

  public Long getZipcode() {
    return zipCode;
  }

  public void setZipCode(Long zipCode) {
    this.zipCode = zipCode;
  }
}
