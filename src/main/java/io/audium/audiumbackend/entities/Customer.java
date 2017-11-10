package io.audium.audiumbackend.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="customer")
@PrimaryKeyJoinColumn(name = "accountid", referencedColumnName = "accountid")
public class Customer extends Account{

    private java.sql.Date dateofbirth;
    private String gender;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_song",
            joinColumns = @JoinColumn(name = "accountid", referencedColumnName = "accountid"),
            inverseJoinColumns = @JoinColumn(name = "songid", referencedColumnName = "songid"))
    private List<Song> songs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "playlist_follower",
            joinColumns = @JoinColumn(name = "accountid", referencedColumnName = "accountid"),
            inverseJoinColumns = @JoinColumn(name = "cus", referencedColumnName = "songid"))
    private List<Song> playlists;


    public Customer() {
    }

    public Customer(Date dateofbirth, String gender) {
        this.dateofbirth = dateofbirth;
        this.gender = gender;
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
