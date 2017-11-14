package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Album {

    @Id
    private Long   albumId;
    private String title;
    private String year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "artistId"
    )
    private Artist artist;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "albums")
    @JsonIgnore
    private List<Song> songs;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "albums")
    @JsonIgnore
    private List<Customer> followers;

    public Album() {
    }

    public Album(Long albumId, String title, String year) {
        this.albumId = albumId;
        this.title = title;
        this.year = year;
    }

    public Long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public Artist getArtist() {
        return artist;
    }
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
