package io.audium.audiumbackend.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
public class Song {
    @Id
    private Long songId;
    private String title;
    private java.sql.Time duration;
    private String file;
    private String year;
    private Long genreId;
    private Long isExplicit;
    private String lyrics;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "artist_song",
        joinColumns = @JoinColumn(name = "songId", referencedColumnName = "songId"),
        inverseJoinColumns = @JoinColumn(name = "artistId", referencedColumnName = "artistId"))
    private List<Artist> artists;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "album_song",
        joinColumns = @JoinColumn(name = "songId", referencedColumnName = "songId"),
        inverseJoinColumns = @JoinColumn(name = "albumId", referencedColumnName = "albumId"))
    private List<Album> albums;

    public Song() {
    }

    public Song(Long songId, String title, Time duration, Long playsthismonth, Long totalplays, String file, String year, Long genreId, Long isExplicit, String lyrics) {
        this.songId = songId;
        this.title = title;
        this.duration = duration;
        this.file = file;
        this.year = year;
        this.genreId = genreId;
        this.isExplicit = isExplicit;
        this.lyrics = lyrics;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
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

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getIsExplicit() {
        return isExplicit;
    }

    public void setIsExplicit(Long isExplicit) {
        this.isExplicit = isExplicit;
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

    public Artist getArtist(Long artistId) {
        System.out.println(this.artists.get(0).getName());
        return this.artists.get(0);
//        for (Artist artist : artists) {
//            if (artist.getArtistId().equals(artistId)) {
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
