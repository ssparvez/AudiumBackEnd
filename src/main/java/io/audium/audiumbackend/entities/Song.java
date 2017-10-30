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
    private Long playsthismonth;
    private Long totalplays;
    private String file;
    private String year;
    private Long genreid;
    private Long isexplicit;

    @ManyToMany
    @JoinTable(
            name = "artist_song",
            joinColumns = @JoinColumn(name = "songid", referencedColumnName = "songid"),
            inverseJoinColumns = @JoinColumn(name = "artistid", referencedColumnName = "artistid"))
    private List<Artist> artists;

    @ManyToOne
    @JoinTable(
            name = "album_song",
            joinColumns = @JoinColumn(name = "songid", referencedColumnName = "songid"),
            inverseJoinColumns = @JoinColumn(name = "albumid", referencedColumnName = "albumid"))
    private Album album;

    public Song() {
    }

    public Song(Long songid, String title, Time duration, Long playsthismonth, Long totalplays, String file, String year, Long genreid, Long isexplicit, List<Artist> artists) {
        this.songid = songid;
        this.title = title;
        this.duration = duration;
        this.playsthismonth = playsthismonth;
        this.totalplays = totalplays;
        this.file = file;
        this.year = year;
        this.genreid = genreid;
        this.isexplicit = isexplicit;
        this.artists = artists;
        this.album = album;
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

    public Long getPlaysthismonth() {
        return playsthismonth;
    }

    public void setPlaysthismonth(Long playsthismonth) {
        this.playsthismonth = playsthismonth;
    }

    public Long getTotalplays() {
        return totalplays;
    }

    public void setTotalplays(Long totalplays) {
        this.totalplays = totalplays;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
