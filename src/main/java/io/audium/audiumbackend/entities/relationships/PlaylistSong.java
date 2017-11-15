package io.audium.audiumbackend.entities.relationships;

import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.Song;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "playlist_song")
public class PlaylistSong implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "songId")
    private Song song;

    @Id
    @ManyToOne
    @JoinColumn(name = "playlistId")
    private Playlist playlist;

    private Timestamp timeAdded;

    public Timestamp getTimeAdded() {
        return timeAdded;
    }
    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public Song getSong() {
        return song;
    }
    public void setSong(Song song) {
        this.song = song;
    }
    public Playlist getPlaylist() {
        return playlist;
    }
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PlaylistSong that = (PlaylistSong)o;
        if ((this.song == null && that.song == null)
            || (this.song != null && that.song != null && this.song.getSongId() == that.song.getSongId())) {
            return true;
        }
        if ((this.playlist == null && that.playlist == null)
            || (this.playlist != null && that.playlist != null
            && this.playlist.getPlaylistId() == that.playlist.getPlaylistId())) {
            return true;
        }
        return false;
    }
    public int hashCode() {
        int songHash;
        int playlistHash;
        if (song == null) {
            songHash = 0;
        } else {
            songHash = song.hashCode();
        }
        if (playlist == null) {
            playlistHash = 0;
        } else {
            playlistHash = playlist.hashCode();
        }
        return (songHash + playlistHash);
    }
}
