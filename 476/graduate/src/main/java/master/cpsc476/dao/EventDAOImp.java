/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import master.cpsc476.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public Event createEvent(Event event, User user) {
        try{
            System.out.println("inside event dao create event:"+event.getTitle());
            event.setCreatedBy(user);
            user.getCreatedEvents().add(event);
            entityManager.persist(event);
            entityManager.merge(user);
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
    public SortedSet<Event> findAll() {
        System.out.println("inside event dao find all");
        //String SQL = "select e from Event e order by e.eventTime asc";
        //Query query = entityManager.createQuery(SQL);
        //List<Event> result = query.getResultList();
        CriteriaBuilder CB = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = CB.createQuery(Event.class);
        Root<Event> from = criteriaQuery.from(Event.class);
        criteriaQuery.orderBy(CB.asc(from.get("eventTime")));
        List<Event> result =entityManager.createQuery(criteriaQuery).getResultList();
        
        result.sort((e1, e2) -> e1.compareTo(e2));
        return  new TreeSet<Event> (result);
    }

    @Override
    public SortedSet<Event> findAllEventsActive() {
        System.out.println("inside event dao find all Events Active");
        String SQL = "select e from Event e order by e.eventTime asc";
        try{
            Query query = entityManager.createQuery(SQL);
            List<Event> result = query.getResultList();
            result.sort((e1, e2) -> e1.compareTo(e2));
            return  new TreeSet<Event> (result);
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
        
    }

    @Override
    public Set<Event> findEventsCreatedByUser(User user /*TODO doc change param from userId to user*/) {
        System.out.println("inside event dao findEventsCreatedByUser user:"+user);
        try{
            return new TreeSet( user.getCreatedEvents()); 
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }       
    }
    
    @Override
    public Set<Event> findEventsInterestedByUser(User user /*TODO doc change param from userId to user*/) {
        System.out.println("inside event dao findEventsCreatedByUser user:"+user);
        try{
            return new TreeSet(user.getInterestedEvents());
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
    }
    
}
