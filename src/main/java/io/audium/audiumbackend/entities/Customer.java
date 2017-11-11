package io.audium.audiumbackend.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "accountId", referencedColumnName = "accountId")
public class Customer extends Account {

    private java.sql.Date dateOfBirth;
    private String gender;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "customer_song",
        joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"),
        inverseJoinColumns = @JoinColumn(name = "songId", referencedColumnName = "songId"))
    private List<Song> songs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "playlist_follower",
        joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "accountId"),
        inverseJoinColumns = @JoinColumn(name = "playlistId", referencedColumnName = "playlistId"))
    private List<Playlist> playlists;


    public Customer() {
    }

    public Customer(Date dateOfBirth, String gender) {
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }


    public java.sql.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.sql.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
