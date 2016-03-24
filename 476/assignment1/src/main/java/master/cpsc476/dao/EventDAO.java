/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;

import java.util.List;
import javax.sql.DataSource;
import master.cpsc476.*;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Mussa 
 */
public interface EventDAO {
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
    public List<Event> findAll();
    public List<Event> findAllEventsActive();
    public List<Event> findEventsCreatedByUser(Long userId);
    public List<Event> findEventsInterestedByUser(Long userId);
    public Event findById(Long id);
    public Event createEvent(Event event);
    
    
}
