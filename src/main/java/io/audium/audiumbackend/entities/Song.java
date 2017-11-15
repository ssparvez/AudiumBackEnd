package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.relationships.AlbumSong;
import io.audium.audiumbackend.entities.relationships.CustomerSong;
import io.audium.audiumbackend.entities.relationships.PlaylistSong;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
public class Song {

    @Id
    private Long   songId;
    private String title;
    private Time   duration;
    private String file;
    private String year;
    private Long   genreId;
    private Long   isExplicit;
    private String lyrics;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
        name = "artist_song",
        joinColumns = @JoinColumn(name = "songId", referencedColumnName = "songId"),
        inverseJoinColumns = @JoinColumn(name = "artistId", referencedColumnName = "artistId"))
    private List<Artist> artists;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
    @JsonIgnore
    private List<AlbumSong> albumSongs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
    @JsonIgnore
    private List<PlaylistSong> playlistSongs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
    @JsonIgnore
    private List<CustomerSong> customerSongs;

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

    public Time getDuration() {
        return duration;
    }
    public void setDuration(Time duration) {
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
        System.out.println(this.artists.get(0).getArtistName());
        return this.artists.get(0);
    }

    public List<AlbumSong> getAlbumSongs() {
        return albumSongs;
    }
    public void setAlbumSongs(List<AlbumSong> albumSongs) {
        this.albumSongs = albumSongs;
    }

    public List<CustomerSong> getCustomerSongs() {
        return customerSongs;
    }
    public void setCustomerSongs(List<CustomerSong> customerSongs) {
        this.customerSongs = customerSongs;
    }
    public List<PlaylistSong> getPlaylistSongs() {
        return playlistSongs;
    }
    public void setPlaylistSongs(List<PlaylistSong> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }
}
