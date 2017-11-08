package io.audium.audiumbackend.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Playlist {
    @Id
    private Long playlistid;
    private Long creatorid;
    private String name;
    private String description;
    private Long ispublic;

    @ManyToMany
    @JoinTable(
            name = "playlist_song",
            joinColumns = @JoinColumn(name = "playlistid", referencedColumnName = "playlistid"),
            inverseJoinColumns = @JoinColumn(name = "songid", referencedColumnName = "songid"))
    private List<Song> songs;


    public Playlist() {
    }

    public Playlist(Long playlistid, Long creatorid, String name, String description, Long ispublic, List<Song> songs) {
        this.playlistid = playlistid;
        this.creatorid = creatorid;
        this.name = name;
        this.description = description;
        this.ispublic = ispublic;
        this.songs = songs;
    }



    public Long getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(Long playlistid) {
        this.playlistid = playlistid;
    }

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

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

    public Long getIspublic() {
        return ispublic;
    }

    public void setIspublic(Long ispublic) {
        this.ispublic = ispublic;
    }
}
