package io.audium.audiumbackend.entities.relationships;

import io.audium.audiumbackend.entities.Customer;
import io.audium.audiumbackend.entities.Song;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "customer_song")
public class CustomerSong implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "songId")
    Song song;

    @Id
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Customer customer;

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
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CustomerSong that = (CustomerSong)o;
        if ((this.song == null && that.song == null && this.customer == null && that.customer == null)
            || (this.song != null && that.song != null && this.customer != null && that.customer != null && this.song.getSongId() == that.song.getSongId()
            && this.customer.getAccountId() == that.customer.getAccountId() && this.timeAdded.equals(that.getTimeAdded()))) {
            return true;
        }
        return false;
    }
    public int hashCode() {
        int songHash;
        int customerHash;
        if (song == null) {
            songHash = 0;
        } else {
            songHash = song.hashCode();
        }
        if (customer == null) {
            customerHash = 0;
        } else {
            customerHash = customer.hashCode();
        }
        return (songHash + customerHash);
    }
}
