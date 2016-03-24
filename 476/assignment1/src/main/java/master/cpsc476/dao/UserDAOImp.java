/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Mussa
 */
public class UserDAOImp implements UserDAO{

    //@Autowired private DataSource dataSource;
    //@Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    

    @Override
    public User findById(Long id) {
        System.out.println("inside user dao find by id:"+id);
        String SQL = "select email,name from Mussa.User where id = ?";
        try{
            return jdbcTemplate
                        .queryForObject(SQL, 
                            //ps -> ps.setLong(1,id),
                            new Object[] { id },
                            (rs,rowNum) -> {
                                return new User(id,
                                                rs.getString("name"),
                                                rs.getString("email"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
    }

    @Override
    public User createUser(User user) {
        System.out.println("inside user dao create user:"+user.getName());
        String SQL = new StringBuilder()
                .append("insert into Mussa.User(")
                .append("name,email,password) values ( ")
                .append("?,?,?) ")
                .toString();
        System.out.println("SQL:"+SQL);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try{
            jdbcTemplate.update(
                (Connection con) -> {
                    PreparedStatement pst =
                            con.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, user.getName());
                    pst.setString(2, user.getEmail());
                    pst.setString(3, user.getPassword());
                    return pst;
                },
                keyHolder);
                
            user.setId(keyHolder.getKey().longValue());
            System.out.println("created id:"+user.getId());
            return user;
        }catch(DataAccessException dataAccessException){
            System.out.println(":"+dataAccessException.getMessage()); 
        }
        return null;
    }

    @Override
    public User isMatch(String email, String password) {
        System.out.println("inside user dao isMatch email:"+email+" pass:"+ password);
        String SQL = " select id,email,name from Mussa.User where email = ? and password= ? ";
        try{
            return jdbcTemplate
                        .queryForObject(SQL, 
                            /*  ps -> {ps.setString(1,email);
                                   ps.setString(2,password);
                            },*/
                            new Object[] { email,  password },
                            (rs,rowNum) -> {
                                return new User(rs.getLong("id"),
                                                rs.getString("name"),
                                                rs.getString("email"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
        
    }
   
    @Override
    public boolean likeEvent(User user, Long eventId) {
        System.out.println("inside user dao like Event:"+user.getId()+" eventId:"+eventId);
        String SQL = new StringBuilder()
                .append("insert into Mussa.INTERESTEDEVENTS(")
                .append("userId,eventid) values (?,?) ")
                .toString();
        System.out.println("SQL:"+SQL);
        try{
            jdbcTemplate.update(SQL,
                    new Object[] { user.getId(),eventId });
            return true;
        }catch(DataAccessException dataAccessException){
            System.out.println(":"+dataAccessException.getMessage()); 
            return false;
        }
    }

    @Override
    public boolean unlikeEvent(User user, Long eventId) {
        System.out.println("inside user dao unlike Event:"+user.getId()+" eventId:"+eventId);
        String SQL = new StringBuilder()
                .append("delete from Mussa.INTERESTEDEVENTS ")
                .append(" where EVENTID =? and userid=? ")
                .toString();
        System.out.println("SQL:"+SQL);
        try{
            jdbcTemplate.update(SQL,
                    new Object[] { user.getId(),eventId });
            return true;
        }catch(DataAccessException dataAccessException){
            System.out.println(":"+dataAccessException.getMessage()); 
            return false;
        }
    }

    @Override
    public User findByEmail(String email) {
        System.out.println("inside user dao find by email:"+email);
        String SQL = "select id,email,name from Mussa.User where email = ?";
        try{
            return jdbcTemplate
                        .queryForObject(SQL, 
                            //ps -> ps.setLong(1,id),
                            new Object[] { email },
                            (rs,rowNum) -> {
                                return new User(rs.getLong("id"),
                                                rs.getString("name"),
                                                rs.getString("email"));
                            }
                        );
        }catch(EmptyResultDataAccessException emptyResultDataAccessException){
            System.out.println("There are no result:"+emptyResultDataAccessException.getMessage());
            return null;
        }
    }    
}
