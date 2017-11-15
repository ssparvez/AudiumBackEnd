package io.audium.audiumbackend.entities.relationships;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Song;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "album_song")
public class AlbumSong implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "songId")
    private Song song;

    @Id
    @ManyToOne
    @JoinColumn(name = "albumId")
    private Album album;

    private int trackNumber;

    public int getTrackNumber() {
        return trackNumber;
    }
    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }
    public Album getAlbum() {
        return album;
    }
    public void setAlbum(Album album) {
        this.album = album;
    }
    public Song getSong() {
        return song;
    }
    public void setSong(Song song) {
        this.song = song;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AlbumSong that = (AlbumSong)o;
        if ((this.song == null && that.song == null)
            || (this.song != null && that.song != null && this.song.getSongId() == that.song.getSongId())) {
            return true;
        }
        if ((this.album == null && that.album == null)
            || (this.album != null && that.album != null
            && this.album.getAlbumId() == that.album.getAlbumId())) {
            return true;
        }
        return false;
    }
    public int hashCode() {
        int songHash;
        int albumHash;
        if (song == null) {
            songHash = 0;
        } else {
            songHash = song.hashCode();
        }
        if (album == null) {
            albumHash = 0;
        } else {
            albumHash = album.hashCode();
        }
        return (songHash + albumHash);
    }
}
