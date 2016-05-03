/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import master.cpsc476.forms.EventDetail;

/**
 *
 * @author Mussa
 */
@Entity 
@Access(AccessType.PROPERTY)
@Table(name = "Event")
public class Event implements Comparable<Event>,Serializable{
    
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String title;
    private String description;
    
    @Convert(converter = LocalDateAttributeConverter.class)
   
    private LocalDateTime eventTime;
    private String location;
    
    private User createdBy ;
    
    private Set<User> userInterested= new TreeSet<User>();
    
    public Event(){}   
    public Event(Long id, String title, String description, 
            String location, LocalDateTime eventTime, User createdBy) {
       this.id=id;
       this.title=title;
       this.description=description;
       this.eventTime=eventTime;
       this.location=location;
       this.createdBy =createdBy;
    }

    Event(EventDetail eventDetail) {
       this.id=eventDetail.getId();
       this.title=eventDetail.getTitle();
       this.description=eventDetail.getDescription();
       this.eventTime= LocalDateTime.parse(
                            eventDetail.getEventTime());
       this.location=eventDetail.getLocation();
    }

    public DateTimeFormatter getEventTimeFormat() {
        return DateTimeFormatter.ofPattern("MMM dd,yyyy HH:mm");
    }
       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }
    @Transient
    public String getEventTimeStr() {
        return eventTime.format(this.getEventTimeFormat());
    }
    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @ManyToOne(fetch=FetchType.EAGER ,optional = false)
    @JoinColumn(name = "createdBy")
    public User getCreatedBy() {
        return createdBy;
    }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "interestedEvents")
    public Set<User> getUserInterested() {
        return userInterested;
    }

    public void setUserInterested(Set<User> userInterested) {
        this.userInterested = userInterested;
    }
    
    @Override
    public String toString(){
        return "event: "+this.getId()+" is:"+this.getTitle()
                + " In: "+this.getEventTimeStr()+" At:"+this.getLocation();
    }  

   @Override
    public int compareTo(Event otherEvent) {
        return this.getEventTime().compareTo( otherEvent.getEventTime());
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof Event){
            Event otherEvent = (Event) object;
            return otherEvent.id.equals( this.id);
        }
     return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }
}   
