package io.audium.audiumbackend.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Genre {
    
    @Id
    private Long   genreId;
    private String name;

    public Long getGenreId() {
        return genreId;
    }
    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
}
