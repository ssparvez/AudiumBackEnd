package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class CustomerAccount {
    @Id
    private Long customerid;
    private String username;
    private java.sql.Date dateofbirth;
    private String gender;

    public CustomerAccount() {
    }

    public CustomerAccount(Long customerid, String username, Date dateofbirth, String gender) {
        this.customerid = customerid;
        this.username = username;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public java.sql.Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(java.sql.Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
