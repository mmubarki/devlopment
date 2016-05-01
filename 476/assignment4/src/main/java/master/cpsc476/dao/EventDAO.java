/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;

import java.util.Set;
import master.cpsc476.*;

/**
 *
 * @author Mussa 
 */
public interface EventDAO {
    public Set<Event> findAll();
    public Set<Event> findAllEventsActive();
    public Event findById(Long id);
    public Event createEvent(Event event, User user);
}
