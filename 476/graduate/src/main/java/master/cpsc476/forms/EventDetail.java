/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.forms;

import javax.ws.rs.FormParam;



/**
 *
 * @author Mussa
 */
public class EventDetail  {
    private Long id;
    @FormParam("title")
    private String title;
    @FormParam("description")
    private String description;
    @FormParam("eventTime")
    private String eventTime;
    @FormParam("location")
    private String location;
    
    public EventDetail(){}   
        
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

    public String getEventTime() {
        return eventTime;
    }
    
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    

    }   
