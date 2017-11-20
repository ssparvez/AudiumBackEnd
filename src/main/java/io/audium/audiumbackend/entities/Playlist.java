package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.audium.audiumbackend.entities.relationships.PlaylistSong;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMapping(
  name = "SearchPlaylistMapping",
  classes = {@ConstructorResult(targetClass = Playlist.class, columns = {
    @ColumnResult(name = "playlistId"),
    @ColumnResult(name = "name"),
    @ColumnResult(name = "description"),
    @ColumnResult(name = "isPublic"),
    @ColumnResult(name = "accountId"),
    @ColumnResult(name = "username"),
    @ColumnResult(name = "role")
  })
  }
)
@Entity
public class Playlist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long    playlistId;
  private String  name;
  private String  description;
  private boolean isPublic;

    @Transient
    private boolean followed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    @JsonIgnore
    private Account creator;

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

  public Playlist(Integer playlistId, String name, String description, Boolean isPublic, Integer accountId, String username, String role) {
    this.playlistId = playlistId.longValue();
    this.name = name;
    this.description = description;
    this.isPublic = isPublic;
    this.creator = new Customer(accountId.longValue(), username, role);
  }

  public Playlist(Long playlistId, Account creator, String name, String description, boolean isPublic) {
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

    public Account getCreator() {
        return creator;
    }
    public void setCreator(Account creator) {
        this.creator = creator;
    }

  public boolean isFollowed() {
    return followed;
  }
  public void setFollowed(boolean followed) {
    this.followed = followed;
  }
}
