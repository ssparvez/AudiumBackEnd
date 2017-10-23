package io.audium.audiumbackend.song;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Song {
    @Id
    private String id;
    private String title;
    private String artist;
    private String album;

    public Song() {

    }

    public Song(String id, String title, String artist, String album) {
        super();
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}