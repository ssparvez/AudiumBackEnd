package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.entities.projections.*;
import io.audium.audiumbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SongRepository     songRepository;
    @Autowired
    private AlbumRepository    albumRepository;
    @Autowired
    private ArtistRepository   artistRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        songRepository.findAll().forEach(songs::add); // this line gets from the db and converts data into objects
        for (Song song : songs) {
            System.out.println(song.getTitle());
        }
        return songs;
    }

    //gets song by id from list
    public Song getSong(long id) {
        return songRepository.findBySongId(id); // id has to be string?
    }

    public void addSong(Song song) {
        songRepository.save(song);
    }

    public void updateSong(long id, Song song) {
        songRepository.save(song); // this method finds the object in the db then saves
    }

    public void removeSong(String id) {
        songRepository.delete(id);
    }

    public List<LibraryArtist> getLibraryArtists(long id) {
        return artistRepository.findFollowerByAccountId(id);
    }

    public List<LibrarySong> getLibrarySongs(long accountId) {
        return songRepository.findCustomerSongs(accountId);
    }

    /*public List<Playlist> getLibraryPlaylists(long id) {
        return playlistRepository.findCustomerPlaylists(id);
    }*/

    public List<LibraryPlaylist> getLibraryPlaylists(long accountId) {
        //Customer       customerAccount = customerAccountRepository.findByAccountId(accountId);
        //List<Playlist> playlists       = customerAccount.getPlaylists();
        return playlistRepository.findByFollowerAccountId(accountId);
    }

    // NEEDS WORK
    public List<LibraryAlbum> getLibraryAlbums(long id) {
        return albumRepository.findCustomerAlbums(id);
    }

    public LibraryPlaylist getPlaylist(long playlistId) {
        return playlistRepository.findByPlaylistId(playlistId);
    }
    /*public Playlist getPlaylist(long playlistId) {
        return playlistRepository.findByPlaylistId(playlistId);
    }*/

    public List<PlaylistTrack> getPlaylistSongs(long playlistId) {
        return songRepository.findPlaylistSongs(playlistId);
    }
}
