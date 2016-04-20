/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;

import java.util.List;
import master.cpsc476.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author Mussa
 */
public class EventDAOImp implements EventDAO{
    
    @PersistenceContext
    private EntityManager entityManager;
 
    @Override
    @Transactional 
    public Event createEvent(Event event) {
        try{
            System.out.println("inside event dao create event:"+event.getTitle());
            entityManager.persist(event);
            System.out.println("after created --- id:"+event.getId());
            return event;
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
    }
    
    @Override
    public Event findById(Long id) {
        System.out.println("inside event dao find by id:"+id);
        try{
            return entityManager.find(Event.class, id);
        
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
    }
    @Override
    public List<Event> findAll() {
        System.out.println("inside event dao find all");
        String SQL = "select e from Event e";
        Query query = entityManager.createQuery(SQL);
        return  (List<Event>)query.getResultList();
    }

    @Override
    public List<Event> findAllEventsActive() {
        System.out.println("inside event dao find all Events Active");
        String SQL = "select e from Event e where eventTime > current_timestamp ";
        try{
            Query query = entityManager.createQuery(SQL);
            return (List<Event>) query.getResultList();
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
        
    }

    @Override
    public List<Event> findEventsCreatedByUser(User user /*TODO doc change param from userId to user*/) {
        System.out.println("inside event dao findEventsCreatedByUser user:"+user);
        try{
            return user.getCreatedEvents(); 
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }       
    }
    
    @Override
    public List<Event> findEventsInterestedByUser(User user /*TODO doc change param from userId to user*/) {
        System.out.println("inside event dao findEventsCreatedByUser user:"+user);
        try{
            return user.getInterestedEvents();
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
    }
    
}
