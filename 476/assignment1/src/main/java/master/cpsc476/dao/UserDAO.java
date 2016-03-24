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
 * @author MUSSA
 */
public interface UserDAO {
    //public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
    public User findById(Long id);
    public User findByEmail(String email);
    public User createUser(User user);
    public User isMatch(String email, String password);
    public boolean likeEvent(User user, Long eventId);
    public boolean unlikeEvent(User user, Long eventId);
}
