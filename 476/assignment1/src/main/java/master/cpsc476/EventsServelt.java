/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import master.cpsc476.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
/**
 *
 * @author a
 */
@WebServlet(name = "EventsServelt", urlPatterns = {"/Events"}, loadOnStartup = 1)
public class EventsServelt extends HttpServlet {
    
    private ApplicationContext applicationContext;
    
    private EventDAO eventDAO;
    private UserDAO userDAO;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in Servlet doGet");
        String action = request.getParameter("action");
        System.out.println("action:"+action);
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "signout":
                this.signout(request, response);
                break;
            case "signup":
                this.showSignupForm(request, response);
                break;
            case "create":
                this.showEventForm(request, response);
                break;
            case "view":
                this.viewEvent(request, response);
                break;
            case "userHome":
                this.viewUserHome(request,response);
                break;
            case "likeEvent":
                this.likeEvent(request, response);
                break;
            case"unlikeEvent":
                this.unlikeEvent(request, response);
                break;        
            case "list":
            default:
                this.listEvents(request, response);
                break;
        }
    }

    private void listEvents(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
<<<<<<< HEAD
        List<Event> eventsList = eventDAO.findAllEventsActive();
        System.out.println("eventsCollection :"+eventsList);
        
        
        request.setAttribute("eventsCollection", eventsList);
        request.getRequestDispatcher("/WEB-INF/jsp/EventsList.jsp").
=======
        //Collections.sort(this.eventsCollection.entrySet(), 
          //      (Event event1, Event event2) -> event1.getTime().compareTo(event2.getTime()));
        request.setAttribute("eventsCollection", this.eventsCollection);
        request.getRequestDispatcher("/jsp/EventsList.jsp").
>>>>>>> parent of 3792d5a... fixed order of events list in main page
                forward(request, response);
    }

    private void viewEvent(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("eventId:"+request.getParameter("eventId"));
        if(request.getParameter("eventId")==null|| 
                request.getParameter("eventId").length()==0) return ;
        Long eventId = new Long(request.getParameter("eventId"));
        Event event = this.getEvent(eventId, response);
        if(event == null) return;
        request.setAttribute("event", event);
        request.getRequestDispatcher("/WEB-INF/jsp/EventView.jsp").
                forward(request, response);
    }

    private void showEventForm(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        LocalDate today = LocalDate.now();
        LocalDate nextYeer = today.plus(1,ChronoUnit.YEARS);
        System.out.print("today:"+today);
        System.out.print("nextYeer:"+nextYeer);
        request.setAttribute("minDate", today);
        request.setAttribute("maxDate", nextYeer);
        request.getRequestDispatcher("/WEB-INF/jsp/EventForm.jsp").
            forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "createEvent":
                this.createEvent(request, response);
                break;
            case "createUser":
                this.createUser(request, response);
                break;
            case "signin":
                this.signin(request, response);
                break;
            case "signout":
                this.signout(request,response);
                break;
            case "list":
            default:
                response.sendRedirect("Events");
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    public void init(){
        applicationContext = WebApplicationContextUtils.
                getWebApplicationContext(getServletContext());
        eventDAO = (EventDAO) applicationContext.getBean("EventDAOImp");
        userDAO = (UserDAO) applicationContext.getBean("UserDAOImp");
        Event event = new Event();
        event.setTitle("Summit Event Productions");
        event.setDescription("We are a boutique lighting design production company based in the MD/DC/VA area");
        event.setLocation("2627 e la palma ave");
        event.setTime(LocalDateTime.now().plusDays(7l));
        event.setCreatedBy(1l);
        eventDAO.createEvent(event);
    }

    private Event getEvent(Long eventId, HttpServletResponse response) 
         throws ServletException, IOException {        
        
        if(eventId == null){
            response.sendRedirect("Events");
            return null;
        }
        Event event = null;
        try{
            event = eventDAO.findById(eventId);
            if(event == null){
                response.sendRedirect("Events");
                return null;
            }
        }
        catch(Exception e){
            response.sendRedirect("Events");
            return null;
        }
        
        return event;
    }

    private void createEvent(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
        System.out.println("inside createEvent");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        System.out.println("inside createEvent user:"+user);
<<<<<<< HEAD
        
        Event event = new Event();
        event.setTitle(request.getParameter("title"));
        event.setDescription(request.getParameter("description"));
        event.setLocation(request.getParameter("location"));
        event.setTime(LocalDateTime.parse(
                    request.getParameter("eventDateTime")));
        event.setCreatedBy(user.getId());

        event = eventDAO.createEvent(event);
        if(event == null){
            session.setAttribute("message", "ERROR: can't create events");
            request.getRequestDispatcher("/WEB-INF/jsp/EventForm.jsp").
                forward(request, response);
=======
        if(user == null){
            // user not login
            session.setAttribute("message", "login before to create events");
            response.sendRedirect("Events?action=list");
        }else{
            Event event = new Event();
            event.setTitle(request.getParameter("title"));
            event.setDescription(request.getParameter("description"));
            event.setLocation(request.getParameter("location"));
            event.setTime(LocalDateTime.parse(
                        request.getParameter("eventDateTime")));
            event.setCreatedBy(user.getId());
            long id;
            synchronized(this)
            {
                id = this.eventsSequence++;
                event.setId(id);
                this.eventsCollection.put(id, event);
            }
            this.eventsCollection.put(id, event);
            session.setAttribute("message", "successfull created event:"+event.getTitle());
            user.getCreatedEvents().add(event);
            session.removeAttribute("user");
            session.setAttribute("user", user);
            response.sendRedirect("Events?action=view&eventId=" + id);
>>>>>>> parent of 3792d5a... fixed order of events list in main page
        }
        session.setAttribute("message", "successfull created event:"+event.getTitle());
        //not add but recall getCreated
        /*
            simple add event to user ctreated list is produce unsorted list
            user.getCreatedEvents().add(event);

        */
        user.setCreatedEvents(eventDAO
                .findEventsCreatedByUser(user.getId()));

        System.out.println("user afetr find event created:"+user);
        
        session.removeAttribute("user");
        session.setAttribute("user", user);
        response.sendRedirect("Events?action=view&eventId=" + event.getId());
    }
    private void createUser(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
        String email = request.getParameter("email");
        User user = userDAO.findByEmail(email);
        if(user != null){
            System.out.println("error in create user:"+email+" . it's already exist");
            request.getSession().setAttribute("message","user already exist");
            
            request.getRequestDispatcher("Events?action=list").
                    forward(request, response);
            return;
        }
        user = new User();
        user.setEmail(email);
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("password"));
        user.setInterestedEvents(new ArrayList<Event>());
        user.setCreatedEvents(new ArrayList<Event>());

        user = userDAO.createUser(user);
        if(user == null){
            request.getSession().setAttribute("message", "ERROR: can't create user");
            String sourceJsp = request.getParameter("sourceJsp");
            if(sourceJsp == null){
                sourceJsp = "Events?action=list";
            }else{
                sourceJsp = "/WEB-INF/jsp/"+sourceJsp;
            }
            request.getRequestDispatcher(sourceJsp).//"/WEB-INF/jsp/SignupForm.jsp").
                    forward(request, response);
        }
        System.out.println("create user:"+user.toString());
        request.getSession().setAttribute("user", user);
        response.sendRedirect("Events?action=list");

    }

    private void showSignupForm(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException,IOException{
            System.out.println("signup for :"+request.getParameter("email"));
            request.setAttribute("email", request.getParameter("email"));
            
            request.getRequestDispatcher("/WEB-INF/jsp/SignupForm.jsp").
                forward(request, response);    
    }

    private void signin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();
            
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = null;
        System.out.println("signin for :"+email);
        System.out.println("signin for :"+password);
        
        user = userDAO.isMatch(email, password);
        System.out.println("isMatch:"+user);
        
            
        if(user != null){
            
            user.setInterestedEvents(eventDAO
                    .findEventsInterestedByUser(user.getId()));
            user.setCreatedEvents(eventDAO
                    .findEventsCreatedByUser(user.getId()));

            session.setAttribute("user", user);
            request.getRequestDispatcher("Events?action=userHome&userId="+user.getId()).
                forward(request, response);  
            
        }else{
            session.setAttribute("message", "signin fail");
            String sourceJsp = request.getParameter("sourceJsp");
            if(sourceJsp == null){
                sourceJsp = "Events?action=list";
            }else{
                sourceJsp = "/WEB-INF/jsp/"+sourceJsp;
            }
            
            response.sendRedirect(sourceJsp);
        }
    }

    private void signout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException,IOException {
        
        request.getSession().removeAttribute("user");
        response.sendRedirect("Events?action=list");

    }

    private void viewUserHome(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException,IOException{
        System.out.println("inside viewUserHome");
        Long userId = 0l;
        if(request.getParameter("userId") != null){
            userId = new Long (request.getParameter("userId"));
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("userId:"+userId);
        System.out.println("current user:"+user);
        if(user != null && user.getId().equals(userId)){
            request.getRequestDispatcher("/WEB-INF/jsp/user.jsp").
                forward(request, response); 
        }
        else{
            request.getSession().setAttribute("message","User not found" );
            request.getRequestDispatcher("Events?action=list").
                forward(request, response);
        }
    }

<<<<<<< HEAD
=======
    private List<Event> getCreatedEvents(User user) {
       
        ArrayList<Event> createdEvents =  new ArrayList<>(this.eventsCollection.values());
        createdEvents = createdEvents
                .parallelStream().
                filter(e -> (( (Event) e).getCreatedBy() == user.getId() ))
                .sorted((e1, e2) -> e2.compareTo(e1))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("createdEvents :"+createdEvents);
        
        //createdEvents.sort((e1, e2) -> e2.compareTo(e1));
        return createdEvents;
    }

>>>>>>> parent of 3792d5a... fixed order of events list in main page
    private void likeEvent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException,IOException{
        System.out.println("inside likeEvent");
        Long eventId = 0l;
        Long userId = 0l;
        if(request.getParameter("userId") != null){
            userId = new Long (request.getParameter("userId"));
        }
        if(request.getParameter("eventId") != null){
            eventId = new Long (request.getParameter("eventId"));
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("userId:"+userId);
        System.out.println("current user:"+user);
        if(user != null && user.getId().equals(userId)){
            Event event = eventDAO.findById(eventId);
            System.out.println("select event:"+event);
            if(event != null){
<<<<<<< HEAD
                if(userDAO.likeEvent(user, eventId)){
                    user.getInterestedEvents().add(event);
                    user.getInterestedEvents().sort((e1, e2) -> e1.compareTo(e2));
                    session.removeAttribute("user");
                    session.setAttribute("user", user);
                    session.setAttribute("message", "event:"+event.getTitle()+" added to interested list");
                }
=======
               user.getInterestedEvents().add(event);
               session.removeAttribute("user");
               session.setAttribute("user", user);
               session.setAttribute("message", "event:"+event.getTitle()+" added to interested list");
>>>>>>> parent of 3792d5a... fixed order of events list in main page
            }else{
               session.setAttribute("message", "event is not exist");
            }
        }
        else{
            request.getSession().setAttribute("message","login first" );
        }
        request.getRequestDispatcher("Events?action=list").
                forward(request, response);
    }

    private void unlikeEvent(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException{
        System.out.println("inside unlikeEvent");
        Long eventId = 0l;
        Long userId = 0l;
        if(request.getParameter("userId") != null){
            userId = new Long (request.getParameter("userId"));
        }
        if(request.getParameter("eventId") != null){
            eventId = new Long (request.getParameter("eventId"));
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("userId:"+userId);
        System.out.println("current user:"+user);
        if(user != null && user.getId().equals(userId)){
            Event event = eventDAO.findById(eventId);
            System.out.println("select event:"+event);
            if(event != null){
                if(userDAO.unlikeEvent(user, eventId)){
                    user.getInterestedEvents().remove(event);
                    session.removeAttribute("user");
                    session.setAttribute("user", user);
                    session.setAttribute("message", "event:"+event.getTitle()+" remved from interested list");
                }
            }else{
               session.setAttribute("message", "event is not exist");
            }
        }
        else{
            request.getSession().setAttribute("message","login first" );
        }        
        request.getRequestDispatcher("Events?action=userHome&userId="+userId).
                forward(request, response);
    }
    
}
