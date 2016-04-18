/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476.dao;


import master.cpsc476.*;

/**
 *
 * @author MUSSA
 */
public interface UserDAO {
    public User findById(Long id);
    public User findByEmail(String email);
    public User createUser(User user);
    public User isMatch(User user);
    public boolean likeEvent(User user, Long eventId);
    public boolean unlikeEvent(User user, Long eventId);
}
