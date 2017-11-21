package io.audium.audiumbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@SqlResultSetMapping(
  name = "SearchEventMapping",
  classes = {@ConstructorResult(targetClass = Event.class, columns = {
    @ColumnResult(name = "eventId"),
    @ColumnResult(name = "eventTitle"),
    @ColumnResult(name = "eventDate"),
    @ColumnResult(name = "isCancelled"),
    @ColumnResult(name = "description")
  })
  }
)
@Entity
@Table(name = "Event")
public class Event {

  @Id
  private Long eventId;
  @Column(name = "title")
  private String eventTitle = "Upcoming Event";
  private Date    eventDate;
  @ManyToOne(targetEntity = Address.class)
  @JoinColumn(name = "addressId")
  private Address address;
  private String  description = "";
  private boolean isCancelled = false;

  @ManyToMany(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinTable(
    name = "event_performer",
    joinColumns = @JoinColumn(name = "eventId", referencedColumnName = "eventId"),
    inverseJoinColumns = @JoinColumn(name = "artistId", referencedColumnName = "artistId"))
  private List<Artist> artists;

  public Event() {
  }

  public Event(Integer eventId, String eventTitle, java.util.Date eventDate, Boolean isCancelled, String description) {
    this.eventId = eventId.longValue();
    this.eventTitle = eventTitle;
    this.eventDate = new Date(eventDate.getTime());
    this.isCancelled = isCancelled;
    this.description = description;
  }

  public Event(Long eventId, String eventTitle, Date eventDate, Address address, String description, boolean isCancelled) {
    this.eventId = eventId;
    this.eventTitle = eventTitle;
    this.eventDate = eventDate;
    this.address = address;
    this.description = description;
    this.isCancelled = isCancelled;
  }

  public Long getEventId() {
    return eventId;
  }
  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }
  public String getEventTitle() {
    return eventTitle;
  }
  public void setEventTitle(String eventTitle) {
    this.eventTitle = eventTitle;
  }
  public Date getEventDate() {
    return eventDate;
  }
  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public boolean getIsCancelled() {
    return isCancelled;
  }
  public void setIsCancelled(boolean isCancelled) {
    isCancelled = isCancelled;
  }
  public Address getAddress() {
    return address;
  }
  public void setAddress(Address address) {
    this.address = address;
  }
  public List<Artist> getArtists() {
    return artists;
  }
}
