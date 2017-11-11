package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Album;
import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.entities.Song;
import io.audium.audiumbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
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

    //TESTING
    public void changeArtist() {
        //Song song = songRepository.findBySongId(new Long(1));
        Artist artist = songRepository.findBySongId(1).getArtist(new Long(2));
        artist.setName("T.C.");
        artistRepository.save(artist);
        //System.out.println(song.getTitle());
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

    public List<Artist> getLibraryArtists(long id) {
        return artistRepository.findCustomerArtists(id);
    }

    //
    public List<Song> getLibrarySongs(long id) {
        return songRepository.findCustomerSongs(id);
    }

    public List<Playlist> getLibraryPlaylists(long id) {
        return playlistRepository.findCustomerPlaylists(id);
    }

    // NEEDS WORK
    public List<Album> getLibraryAlbums(long id) {
        return albumRepository.findCustomerAlbums(id);
    }

    public List<Song> getLibraryPlaylistSongs(long accountId, long playlistId) {
        Playlist playlist = playlistRepository.findByPlaylistId(playlistId);
        return playlist.getSongs();
    }
}
