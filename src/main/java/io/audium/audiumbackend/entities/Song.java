package io.audium.audiumbackend.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
public class Song {
    @Id
    private Long songid;
    private String title;
    private java.sql.Time duration;
    private String file;
    private String year;
    private Long genreid;
    private Long isexplicit;
    private String lyrics;

    @ManyToMany
    @JoinTable(
            name = "artist_song",
            joinColumns = @JoinColumn(name = "songid", referencedColumnName = "songid"),
            inverseJoinColumns = @JoinColumn(name = "artistid", referencedColumnName = "artistid"))
    private List<Artist> artists;

    @ManyToMany
    @JoinTable(
            name = "album_song",
            joinColumns = @JoinColumn(name = "songid", referencedColumnName = "songid"),
            inverseJoinColumns = @JoinColumn(name = "albumid", referencedColumnName = "albumid"))
    private List<Album> albums;

    public Song() {
    }

    public Song(Long songid, String title, Time duration, Long playsthismonth, Long totalplays, String file, String year, Long genreid, Long isexplicit, String lyrics) {
        this.songid = songid;
        this.title = title;
        this.duration = duration;
        this.file = file;
        this.year = year;
        this.genreid = genreid;
        this.isexplicit = isexplicit;
        this.lyrics = lyrics;
    }

    public Long getSongid() {
        return songid;
    }

    public void setSongid(Long songid) {
        this.songid = songid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.sql.Time getDuration() {
        return duration;
    }

    public void setDuration(java.sql.Time duration) {
        this.duration = duration;
    }


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getGenreid() {
        return genreid;
    }

    public void setGenreid(Long genreid) {
        this.genreid = genreid;
    }

    public Long getIsexplicit() {
        return isexplicit;
    }

    public void setIsexplicit(Long isexplicit) {
        this.isexplicit = isexplicit;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Artist getArtist(Long artistid) {
        System.out.println(this.artists.get(0).getName());
        return this.artists.get(0);
//        for (Artist artist : artists) {
//            if (artist.getArtistid().equals(artistid)) {
//                return artist;
//            }
//        }
//        return null;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}