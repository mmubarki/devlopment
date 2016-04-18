/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import master.cpsc476.forms.EventDetail;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 * @author Mussa
 */
public class Event implements Comparable<Event>{
       private Long id=null;
       private String title=null;
       private String description=null;
       @DateTimeFormat(iso = ISO.DATE_TIME)
       private LocalDateTime eventTime=null;
       private String location=null;
       private Long createdBy =null;
    public Event(){}   
    public Event(Long id, String title, String description, 
            String location, LocalDateTime time, Long createdBy) {
       this.id=id;
       this.title=title;
       this.description=description;
       this.eventTime=time;
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

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
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
