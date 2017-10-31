package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.entities.Playlist;
import io.audium.audiumbackend.repositories.ArtistRepository;
import io.audium.audiumbackend.repositories.PlaylistRepository;
import io.audium.audiumbackend.repositories.SongRepository;
import io.audium.audiumbackend.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        songRepository.findAll().forEach(songs::add); // this line gets from the db and converts data into objects
        for (Song song: songs) {
            System.out.println(song.getTitle());
        }
        return songs;
    }
    //TESTING
    public void changeArtist() {
        System.out.println("yo");
        //Song song = songRepository.findBySongid(new Long(1));
        Artist artist = songRepository.findBySongid(1).getArtist(new Long(2));
        artist.setName("T.C.");
        artistRepository.save(artist);
        //System.out.println(song.getTitle());
    }


    //gets song by id from list
    public Song getSong(long id) {
        return songRepository.findBySongid(id); // id has to be string?
    }

    public void addSong(Song song) {
        songRepository.save(song);
    }

    public void updateSong(String id, Song song) {
        songRepository.save(song); // this method finds the object in the db then saves
    }

    public void removeSong(String id) {
        songRepository.delete(id);
    }

    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        playlistRepository.findAll().forEach(playlists::add); // this line gets from the db and converts data into objects
        for (Playlist playlist: playlists) {
            System.out.println(playlist.getName());
        }
        return playlists;
    }

    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        artistRepository.findAll().forEach(artists::add); // this line gets from the db and converts data into objects
        return artists;
    }
}
