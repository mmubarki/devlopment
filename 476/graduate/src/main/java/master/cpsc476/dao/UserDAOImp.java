/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import master.cpsc476.*;

/**
 *
 * @author Mussa
 */
public class UserDAOImp implements UserDAO{

    @PersistenceContext private EntityManager  entityManager;

    
    @Override
    @Transactional
    public User createUser(User user) {
        System.out.println("inside user dao create user:"+user.getName());
        try{
            entityManager.persist(user);
            System.out.println("after created --- id:"+user.getId());
            return user;
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public User findById(Long id) {
        System.out.println("inside user dao find by id:"+id);        
        try{
            
            return entityManager.find(User.class, id);
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        System.out.println("inside user dao find by email:"+email);
        String SQL = "select u from User u where u.email = :email";
        try{
            Query query = entityManager.createQuery(SQL);
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
    } 
    
    @Override
    public User isMatch(String email, String password) {
        System.out.println("inside user dao isMatch email:"+email+" pass:"+ password);
        String SQL = " select u from User u where u.email = :email and u.password= :password ";
        try{
            Query query = entityManager.createQuery(SQL);
            query.setParameter("email", email);
            query.setParameter("password", password);
            User user = (User) query.getSingleResult();
            System.out.println("event list size:"+user.getInterestedEvents().size());
            return user;
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return null;
        }
    }
   
    @Override
    @Transactional
    public boolean likeEvent(User user, Event event /* TODO doc change param eventid to class event*/) {
        //entityManager.find(User.class, user.getId());
        System.out.println("inside likeEvent:"+user +" event:"+event);
        System.out.println("getInterestedEvents:"+user.getInterestedEvents());
        if(user.getInterestedEvents().contains(event)){
            return false;
        }
        boolean isAdded = user.getInterestedEvents().add(event);
        event.getUserInterested().add(user);
        try{
            entityManager.merge(user);
            entityManager.merge(event);
            return isAdded;
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean unlikeEvent(User user, Event event /* TODO doc change param eventid to class event*/) {
        //entityManager.find(User.class, user.getId()); 
        boolean isremoved = user.getInterestedEvents().remove(event);
        try{
            entityManager.merge(user);
            return isremoved;
        }catch (PersistenceException e) {
            System.out.println("PersistenceException:"+e.getMessage());
            return false;
        }
    }
   
}
