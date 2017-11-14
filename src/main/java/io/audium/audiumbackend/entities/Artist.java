package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Artist {

    @Id
    private Long   artistId;
    private Long   labelId;
    private Long   accountId;
    private String name;
    private String bio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist")
    private List<Album> albums;

    /*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
    @JsonIgnore
    private List<Song> songs;*/

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
    @JsonIgnore
    private List<Customer> followers;

    public Artist() {
    }

    public Long getArtistId() {
        return artistId;
    }
    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getLabelId() {
        return labelId;
    }
    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    /*public List<Song> getSongs() {
        return this.songs;
    }
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }*/

    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
