/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.io.Serializable;;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import master.cpsc476.forms.LoginDetail;
import master.cpsc476.forms.SignupDetail;

/**
 *
 * @author Mussa
 */
@Entity
@Table(name = "Users")

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String name ;
    private String email ;
    private String password ;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @OrderBy("eventTime ASC")
    @JoinTable(
        name = "InterestedEvents", 
        joinColumns = @JoinColumn(name = "userId",referencedColumnName ="id") , 
        inverseJoinColumns =  @JoinColumn(name = "eventId",referencedColumnName ="id") 
    )
    
    private Set<Event> interestedEvents= new TreeSet<Event>() ;
    
    @OneToMany(mappedBy = "createdBy", fetch =FetchType.EAGER )
    @OrderBy("eventTime ASC")
    private Set<Event> createdEvents= new TreeSet<Event>() ;
    
    public User(){}
    public User(Long id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    User(SignupDetail signupDetail) {
        this.setName(signupDetail.getName());
        this.setEmail(signupDetail.getEmail());
        this.setPassword(signupDetail.getPassword());
    }
    User(LoginDetail loginDetail) {
        this.setEmail(loginDetail.getEmail());
        this.setPassword(loginDetail.getPassword());
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

    public Set<Event> getInterestedEvents() {
        return interestedEvents;
    }

    public void setInterestedEvents(Set<Event> interestedEvents) {
        this.interestedEvents = interestedEvents;
    }

    public Set<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(Set<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }
    
    @Override
    public String toString(){
        return "user id:"+this.id+" name:"+this.name+" email:"+this.email;
    }
    
}
