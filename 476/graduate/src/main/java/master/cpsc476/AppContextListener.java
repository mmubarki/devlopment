/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

/**
 *
 * @author Mussa
 */
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import master.cpsc476.dao.EventDAO;
import master.cpsc476.dao.UserDAO;
 
@WebListener
public class AppContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("contextInitialized: initialize for Application");
        initializeApplication();
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("contextDestroyed: Destroye for Application"); 
    }
    @Inject
    EventDAO eventDAO;

   @Inject
   UserDAO userDAO;
    private void initializeApplication() {
        System.out.println("inside EventsService initializeApplication");
        User user = new User(null, "mussa mubarki", "mmubarki@gmail.com");
        user.setPassword("a");
       
        user = userDAO.createUser(user);
        System.out.println("@@@create user:"+user);
        
        Event event = new Event();
        event.setTitle("Summit Event Productions");
        event.setDescription("We are a boutique lighting design production company based in the MD/DC/VA area");
        event.setLocation("2627 e la palma ave");
        event.setEventTime(LocalDateTime.now().plusDays(7l));
        event = eventDAO.createEvent(event,user);
        System.out.println("@@@create event:"+event);
        
        Event event1 = new Event();
        event1.setTitle("Fun On Wheels");
        event1.setDescription("Fun On Wheels design production company based in the MD/DC/VA area");
        event1.setLocation("2627 e la palma ave");
        event1.setEventTime(LocalDateTime.now().plusDays(90l));
        event1 = eventDAO.createEvent(event1,user);
        System.out.println("@@@create event:"+event1);
        
        System.out.println("@@@findAll event:"+eventDAO.findAll());
    }
     
}    
