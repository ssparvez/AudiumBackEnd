package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.relationships.PlaylistSong;

import javax.persistence.*;
import java.util.List;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long playlistId;
    private String name;
    private String description;
    private boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Customer creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlist")
    private List<PlaylistSong> playlistSongs;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "playlists")
    @JsonIgnore
    private List<Customer> followers;

    /*@ManyToMany(targetEntity = Song.class, fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(formula = @JoinFormula(value = "(SELECT PS.song FROM PlaylistSong PS WHERE PS.playlist.playlistId = playlistId)", referencedColumnName = "songId")), // (SELECT a.id FROM A a WHERE a.uuid = uuid)
        @JoinColumnOrFormula(column = @JoinColumn(table = "PlaylistSong", name = "playlistId", referencedColumnName = "playlistId"))})
    private List<Song> songs;*/

    public Playlist() {
    }

    public Playlist(Long playlistId, Customer creator, String name, String description, boolean isPublic) {
        this.playlistId = playlistId;
        this.creator = creator;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
    }

    public Long getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    //public Long getAccountId() { return accountId; }
    //public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }
    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    //public List<Song> getSongs() { return songs; }
    //public void setSongs(List<Song> songs) { this.songs = songs; }

    public List<Customer> getFollowers() {
        return followers;
    }
    public void setFollowers(List<Customer> followers) {
        this.followers = followers;
    }

    public List<PlaylistSong> getPlaylistSongs() {
        return playlistSongs;
    }
    public void setPlaylistSongs(List<PlaylistSong> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    public Customer getCreator() {
        return creator;
    }
    public void setCreator(Customer creator) {
        this.creator = creator;
    }

    /*public List<Song> getSongs() {
        return this.songs;
    }
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }*/
}
