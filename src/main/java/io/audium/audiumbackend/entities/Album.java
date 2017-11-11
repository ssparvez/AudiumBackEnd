package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Album {
    @Id
    private Long albumId;
    private String title;
    private String year;

    @ManyToOne
    @JoinColumn(
        name = "artistId"
    )
    private Artist artist;

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
