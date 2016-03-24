/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import master.cpsc476.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Mussa
 */
public class EventDAOImp implements EventDAO{
    
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Event> findAll() {
        System.out.println("inside event dao find all");
        String SQL = "select id,title,description,location,eventTime,createdBy from Mussa.Event ";
        try{
            return jdbcTemplate
                        .query(SQL, 
                            (rs,rowNum) -> {
                                return new Event(rs.getLong("id"),
                                                rs.getString("title"),
                                                rs.getString("description"),
                                                rs.getString("location"),
                                                rs.getTimestamp("eventTime").toLocalDateTime(),
                                                rs.getLong("createdBy"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public List<Event> findAllEventsActive() {
        System.out.println("inside event dao find all Events Active");
        String SQL = "select id,title,description,location,eventTime,createdBy from Mussa.Event where eventTime > current_timestamp ";
        try{
            return jdbcTemplate
                        .query(SQL, 
                            (rs,rowNum) -> {
                                return new Event(rs.getLong("id"),
                                                rs.getString("title"),
                                                rs.getString("description"),
                                                rs.getString("location"),
                                                rs.getTimestamp("eventTime").toLocalDateTime(),
                                                rs.getLong("createdBy"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public List<Event> findEventsCreatedByUser(Long userId) {
        System.out.println("inside event dao findEventsCreatedByUser userId:"+userId);
        String SQL = "select id,title,description,location,eventTime,createdBy from Mussa.Event where createdBy = ? ";
        try{
            return jdbcTemplate
                        .query(SQL, 
                            //new Long[]{userId},
                            ps -> ps.setLong(1,userId),
                            (rs,rowNum) -> {
                                return new Event(rs.getLong("id"),
                                                rs.getString("title"),
                                                rs.getString("description"),
                                                rs.getString("location"),
                                                rs.getTimestamp("eventTime").toLocalDateTime(),
                                                rs.getLong("createdBy"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
    }
    
    @Override
    public List<Event> findEventsInterestedByUser(Long userId) {
        System.out.println("inside event dao findEventsCreatedByUser userId:"+userId);
        String SQL = new StringBuilder()
                .append("select id,title,description,location,eventTime,")
                .append("createdBy from Mussa.Event where id in ")
                .append(" (select eventid from MUSSA.INTERESTEDEVENTS where userid = ?)")
                .toString();
        try{
            return jdbcTemplate
                        .query(SQL, 
                            //new Long[]{userId},
                            ps -> ps.setLong(1,userId),
                            (rs,rowNum) -> {
                                return new Event(rs.getLong("id"),
                                                rs.getString("title"),
                                                rs.getString("description"),
                                                rs.getString("location"),
                                                rs.getTimestamp("eventTime").toLocalDateTime(),
                                                rs.getLong("createdBy"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
    }
    @Override
    public Event findById(Long id) {
        System.out.println("inside event dao find by id:"+id);
        String SQL = "select id,title,description,location,eventTime,createdBy from Mussa.Event where id = ?";
        try{
            return jdbcTemplate
                        .queryForObject(SQL, 
                            //ps -> ps.setLong(1,id),
                            new Object[] { id },
                            (rs,rowNum) -> {
                                return new Event(id,
                                                rs.getString("title"),
                                                rs.getString("description"),
                                                rs.getString("location"),
                                                rs.getTimestamp("eventTime").toLocalDateTime(),
                                                rs.getLong("createdBy"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public Event createEvent(Event event) {
        System.out.println("inside user dao create event:"+event.getTitle());
        String SQL = new StringBuilder()
                .append("insert into Mussa.Event(")
                .append("title,description,location,eventTime,")
                .append("createdBy) values ( ")
                .append("?,?,?,?,?) ")
                .toString();
        System.out.println("SQL:"+SQL);
        System.out.println("Timestamp.valueOf(event.getTime()):"+Timestamp.valueOf(event.getTime()));
        System.out.println("event.getCreatedBy():"+event.getCreatedBy());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try{
            jdbcTemplate.update(
                (Connection con) -> {
                    PreparedStatement pst =
                            con.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, event.getTitle());
                    pst.setString(2, event.getDescription());
                    pst.setString(3, event.getLocation());
                    pst.setTimestamp(4, Timestamp.valueOf(event.getTime()));
                    pst.setLong(5, event.getCreatedBy());
                    return pst;
                },
                keyHolder);
                 
            event.setId(keyHolder.getKey().longValue());
            System.out.println("created Id:"+event.getId());
            return event;
        }catch(DataAccessException dataAccessException){
            System.out.println(":"+dataAccessException.getMessage()); 
        }
        return null;
    }

    

    
}
