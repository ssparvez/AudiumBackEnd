package io.audium.audiumbackend.services;

import io.audium.audiumbackend.entities.Artist;
import io.audium.audiumbackend.repositories.ArtistRepository;
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

    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        songRepository.findAll().forEach(songs::add); // this line gets from the db and converts data into objects
        for (Song song: songs) {
            System.out.println(song.getTitle());
        }
        System.out.println(songs.toString());
        return songs;
    }
    //TESTING
    public void changeArtist() {
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
}
