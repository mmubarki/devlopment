/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Mussa
 */
public class User {
    private Long id = null;
    private String name = null;
    private String email = null;
    private String password = null;
    private List<Event> interestedEvents = null;
    private List<Event> createdEvents = null;
    public User(){}
    public User(Long id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getInterestedEvents() {
        return interestedEvents;
    }

    public void setInterestedEvents(List<Event> interestedEvents) {
        this.interestedEvents = interestedEvents;
    }

    public List<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(List<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }
    
    @Override
    public String toString(){
        return "user id:"+this.id+" name:"+this.name+" email:"+this.email;
    }
    
}
