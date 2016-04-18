/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author a
 */
@WebServlet(name = "EventsServelt", urlPatterns = {"/Events"}, loadOnStartup = 1)
public class EventsServelt extends HttpServlet {
    
    private Map<Long,Event> eventsCollection = new HashMap();
    private Map<String,User> usersCollection = new HashMap();
    private long usersSequence = 1;
    private long eventsSequence = 1;
    
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
        // filter old events
        System.out.println("eventsCollection before remove old event :"+
                this.eventsCollection);
        /*
        Map<Long,Event> filteredHashMap = this.eventsCollection.entrySet()
            .parallelStream()
            .filter(e -> (( (Event) e.getValue()).getTime().isAfter( LocalDateTime.now()) ))
            .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
            .collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
        System.out.println("filteredHashMap afetr remove old event and sort :"+filteredHashMap);
        */
        ArrayList<Event> filterSortedEvents =  new ArrayList<>(this.eventsCollection.values());
        filterSortedEvents = filterSortedEvents
                .parallelStream()
                .filter(e -> (( (Event) e).getTime().isAfter( LocalDateTime.now()) ))
                .sorted((e1, e2) -> e1.compareTo(e2))
                .collect(Collectors.toCollection(ArrayList::new));
        
        System.out.println("filterSortedEvents :"+filterSortedEvents);
        
        request.setAttribute("eventsCollection", filterSortedEvents);
        request.getRequestDispatcher("/jsp/EventsList.jsp").
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
        request.getRequestDispatcher("/jsp/EventView.jsp").
                forward(request, response);
    }

    private void showEventForm(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user == null){
            // user not login
            session.setAttribute("message", "login before to show event form");
            response.sendRedirect("Events?action=list");
        }else{
            LocalDate today = LocalDate.now();
            LocalDate nextYeer = today.plus(1,ChronoUnit.YEARS);
            System.out.print("today:"+today);
            System.out.print("nextYeer:"+nextYeer);
            request.setAttribute("minDate", today);
            request.setAttribute("maxDate", nextYeer);
            request.getRequestDispatcher("/jsp/EventForm.jsp").
                forward(request, response);
        }
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
        //initialize  collection
        
        User user = new User();
        user.setEmail("mmubarki@gmail.com");
        user.setName("mussa");
        user.setPassword("123456");
        user.setCreatedEvents(new ArrayList());
        user.setInterestedEvents(new ArrayList());
        long id;
        synchronized(this){
            id = this.usersSequence++;
            user.setId(id);
        }
        this.usersCollection.put(user.getEmail(), user);
        
        Event event = new Event();
        event.setTitle("Summit Event Productions");
        event.setDescription("We are a boutique lighting design production company based in the MD/DC/VA area");
        event.setLocation("2627 e la palma ave");
        event.setTime(LocalDateTime.parse("Jan 21,2017 13:00",event.getEventTimeFormat()));
        event.setCreatedBy(user.getId());
        synchronized(this)
        {
            id = this.eventsSequence++;
            event.setId(id);
            this.eventsCollection.put(id, event);
        }
        event = new Event();
        event.setTitle("Impulse Event Lighting");
        event.setDescription("Have a Memorable Wedding with help from our experienced Lighting Technicians! Impulse Event Lighting works closely with our customers to ensure complete satisfaction. Click below and see what our previous customers have had to say");
        event.setLocation("516 Cameron St, Placentia");
        event.setTime(LocalDateTime.parse("Dec 01,2016 11:30",event.getEventTimeFormat()));
        event.setCreatedBy(user.getId());
        synchronized(this){
            id = this.eventsSequence++;
            event.setId(id);
            this.eventsCollection.put(id, event);
        }
        System.out.println("in init usersCollection:"+usersCollection);
        System.out.println("in init eventsCollection:"+eventsCollection);
    }

    private Event getEvent(Long eventId, HttpServletResponse response) 
         throws ServletException, IOException {        
        
        if(eventId == null || eventId == 0){
            response.sendRedirect("/assignment1/Events");
            return null;
        }
        Event event = null;
        try{
            event = this.eventsCollection.get(eventId);
            if(event == null){
                response.sendRedirect("/assignment1/Events");
                return null;
            }
        }
        catch(Exception e){
            response.sendRedirect("/assignment1/Events");
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
            //not add but recall getCreated
            /*
                simple add event to user ctreated list is produce unsorted list
                user.getCreatedEvents().add(event);
                
            */
            user.setCreatedEvents(getCreatedEvents(user));
            session.removeAttribute("user");
            session.setAttribute("user", user);
            response.sendRedirect("Events?action=view&eventId=" + id);
        }
    }
    private void createUser(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
        String email = request.getParameter("email");
        if(this.usersCollection.containsKey(email)){
            System.out.println("error in create user:"+email+" . it's already exist");
            request.getSession().setAttribute("message","user already exist");
            response.sendRedirect("Events?action=list");
            return;
        }
        User user = new User();
        user.setEmail(email);
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("password"));
        user.setInterestedEvents(new ArrayList<Event>());
        user.setCreatedEvents(new ArrayList<Event>());

        long id;
        synchronized(this)
        {
            id = this.usersSequence++;
            user.setId(id);
        }
        this.usersCollection.put(user.getEmail(), user);
        System.out.println("create user:"+user.toString());
        request.getSession().setAttribute("user", user);
        response.sendRedirect("Events?action=list");

    }

    private void showSignupForm(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException,IOException{
            System.out.println("signup for :"+request.getParameter("email"));
            request.setAttribute("email", request.getParameter("email"));
            
            request.getRequestDispatcher("/jsp/SignupForm.jsp").
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
        
        Map<String,User> filteredHashMap = this.usersCollection.entrySet()
            .parallelStream()
            .filter(e -> (((User)e.getValue()).getEmail().equals(email) &&
                    ((User)e.getValue()).getPassword().equals(password) ))
            .collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
        System.out.println("filteredHashMap :"+filteredHashMap);
        
        if(! filteredHashMap.isEmpty()){
            user = filteredHashMap.get(email);
        }
        if(user != null){
            user.setCreatedEvents(getCreatedEvents(user));
            session.setAttribute("user", user);
            response.sendRedirect("Events?action=userHome&userId="+user.getId());
        }else{
            session.setAttribute("message", "signin fail");
            response.sendRedirect("Events?action=list");
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
            request.getRequestDispatcher("/jsp/user.jsp").
                forward(request, response); 
        }
        else{
            request.getSession().setAttribute("message","User not found" );
            response.sendRedirect("Events?action=list");
        }
    }

    private List<Event> getCreatedEvents(User user) {
       
        ArrayList<Event> createdEvents =  new ArrayList<>(this.eventsCollection.values());
        createdEvents = createdEvents
                .parallelStream()
                .filter(e -> (( (Event) e).getCreatedBy() == user.getId() ))
                .sorted((e1, e2) -> e1.compareTo(e2))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("createdEvents :"+createdEvents);
        
        return createdEvents;
    }

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
            Event event = this.eventsCollection.get(eventId);
            System.out.println("select event:"+event);
            if(event != null){
               user.getInterestedEvents().add(event);
               user.getInterestedEvents().sort((e1, e2) -> e1.compareTo(e2));
               session.removeAttribute("user");
               session.setAttribute("user", user);
               session.setAttribute("message", "event:"+event.getTitle()+" added to interested list");
            }else{
               session.setAttribute("message", "event is not exist");
            }
        }
        else{
            request.getSession().setAttribute("message","login first" );
        }
        response.sendRedirect("Events?action=list");
        //request.getRequestDispatcher("Events?action=list").
          //      forward(request, response); 
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
            Event event = this.eventsCollection.get(eventId);
            System.out.println("select event:"+event);
            if(event != null){
               user.getInterestedEvents().remove(event);
               session.removeAttribute("user");
               session.setAttribute("user", user);
               session.setAttribute("message", "event:"+event.getTitle()+" removed from interested list");
            }else{
               session.setAttribute("message", "event is not exist");
            }
        }
        else{
            request.getSession().setAttribute("message","login first" );
        }
        response.sendRedirect("Events?action=userHome&userId="+userId);
    }
    
}
