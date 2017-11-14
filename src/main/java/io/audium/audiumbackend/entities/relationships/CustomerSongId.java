package io.audium.audiumbackend.entities.relationships;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class CustomerSongId {

    @Id
    protected Long songId;
    @Id
    protected Long accountId;
}
