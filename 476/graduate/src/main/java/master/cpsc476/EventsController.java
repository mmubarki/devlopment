/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javax.mvc.Controller;
//import javax.mvc.Models;
import com.oracle.ozark.core.Models;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
//import javax.mvc.annotation.RedirectScoped;
import javax.servlet.http.HttpSession;
import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import master.cpsc476.dao.EventDAO;
import master.cpsc476.dao.UserDAO;
import master.cpsc476.forms.EventDetail;
import master.cpsc476.forms.LoginDetail;
import master.cpsc476.forms.SignupDetail;

/**
 *
 * @author Mussa
 */

@Controller
@Path("Events")
public class EventsController {
    
    @Inject
    Models models;
    
    
    @Inject
    EventDAO eventDAO;

   @Inject
   UserDAO userDAO;
    private final String PREFIX = "/WEB-INF/jsp/";
    private final String SUFFIX=".jsp";
    
    @PostConstruct
    public void init(){         
        
    }
    
    //javax.mvc.annotation.RedirectScoped
    //@RedirectScoped
    @GET
    @Path("list")
    public String listEvents( @QueryParam("message") String message){
        System.out.println("message :"+message);
        Set<Event> eventsList =  eventDAO.findAllEventsActive();
        System.out.println("inside listEvents eventsCollection :"+eventsList);
        
        models.put("eventsCollection", eventsList);
        //models.put("loginDetail", new LoginDetail());
        if(message != null){
            models.put("message", message);
        }
        return PREFIX+"EventsList"+SUFFIX;
    }

    //@RequestMapping(value="/Events/view/{id}", method = RequestMethod.GET)
    @GET
    @Path("view/{id}")
    public String viewEvent(@PathParam("id") Long id, @Context HttpServletRequest request){
        System.out.println("id:"+id);
        if(id == null){
            return "redirect:/Events/list";
        }
        Event event = eventDAO.findById(id);
        
        if(event == null){
                return "redirect:/Events/list";
        }
        models.put("event", event);
        return PREFIX+"EventView"+SUFFIX;
    }
    
    //@RequestMapping(value="/Events/create", method = RequestMethod.GET)
    @GET
    @Path("create")
    public String showEventForm()
    {
        System.out.print("in /create before redirct");
        LocalDate today = LocalDate.now();
        LocalDate nextYeer = today.plus(1,ChronoUnit.YEARS);
        System.out.print("today:"+today);
        System.out.print("nextYeer:"+nextYeer);
        models.put("minDate", today);
        models.put("maxDate", nextYeer);
        return PREFIX+"EventForm"+SUFFIX;
    }


    
    //@RequestMapping(value="/Events/createEvent", method = RequestMethod.POST)
    @POST
    @Path("createEvent")
    public String createEvent(@BeanParam EventDetail eventDetail, 
            @QueryParam(value="sourceJsp") String sourceJsp,
            @Context HttpServletRequest request){
        System.out.println("inside createEvent");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        System.out.println("inside createEvent user:"+user);
        System.out.println("inside createEvent eventDetail:"+eventDetail);
        Event event = new Event(eventDetail);
        
        event = eventDAO.createEvent(event,user);
        if(event == null){
            session.setAttribute("message", "ERROR: can't create events");
            return "EventForm";
        }
        String message ="successfull created event:"+event.getTitle();
        session.setAttribute("message", message);

        System.out.println("user afetr find event created:"+user);
        
        session.removeAttribute("user");
        session.setAttribute("user", user);
        return "redirect:/Events/view/" + event.getId();
    }
    
    //@RequestMapping(value="/Events/createUser", method = RequestMethod.POST)
    @POST
    @Path("createUser")
    public String createUser(@BeanParam SignupDetail signupDetail, 
            @FormParam(value="sourceJsp") String sourceJsp,
            @Context HttpServletRequest request){
        System.out.println("@@@in create user");
            
        User user = userDAO.findByEmail(signupDetail.getEmail());
        HttpSession session = request.getSession();
        if(user != null){
            System.out.println("error in create user:"+user.getEmail()+" . Email already exist");
            String message ="user already exist";
            session.setAttribute("message", message);

            //models.put("signupDetail",signupDetail);
            return PREFIX+"SignupForm"+SUFFIX;
        }
        user = new User(signupDetail);
        
        System.out.println("will create user:"+user.toString());
        user = userDAO.createUser(user);
        if(user == null){
            String message ="ERROR: can't create user";
            session.setAttribute("message", message);

            if(sourceJsp == null){
                return "redirect:/Events/list";
            }else{
                return sourceJsp;
            }
        }
        session.setAttribute("user", user);
        return"redirect:/Events/list";
    }
    

    //@RequestMapping(value={"/Events/signup*"}, method = RequestMethod.GET)
    @GET
    @Path("signup")
    public String showSignupForm(
        @QueryParam(value="email") String email) {
        System.out.println("signup for :"+email);
        if(email != null){
            SignupDetail signupDetail = new SignupDetail();
            signupDetail.setEmail(email);
            models.put("signupDetail", signupDetail);
        }
        return  PREFIX+"SignupForm"+SUFFIX;    
    }

    //@RequestMapping(value="/Events/signin", method = RequestMethod.POST)
    @POST
    @Path("signin")
    public String signin(@BeanParam LoginDetail loginDetail, 
            @QueryParam(value="sourceJsp") String sourceJsp,
             @Context HttpServletRequest request) {
        
        String email = loginDetail.getEmail();
        String password = loginDetail.getPassword();
        System.out.println("signin for :"+email);
        System.out.println("signin for :"+password);
        
        User user = userDAO.isMatch(email, password);
        System.out.println("isMatch:"+user);
        HttpSession session = request.getSession();    
        if(user != null){           
            
            session.setAttribute("user", user);
            return "redirect:/Events/userHome/"+user.getId();
            
        }else{
            String message ="signin fail";
            session.setAttribute("message", message);

            System.out.println("sourceJSP:"+sourceJsp);
            
            if(sourceJsp == null || "".equals(sourceJsp)){
                return "redirect:/Events/list";
            }else{
                return sourceJsp;
            }
        }
    }

    //@RequestMapping(value="/Events/signout", method = RequestMethod.GET)
    @GET
    @Path("signout")
    public String signout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        String message ="user successfully signout";
        session.setAttribute("message", message);

        return "redirect:/Events/list";

    }

    //@RequestMapping(value="/Events/userHome/{id}", method = RequestMethod.GET)
    @GET
    @Path("userHome/{id}")
    public String viewUserHome(@PathParam("id") Long id, 
            @QueryParam(value="message") String message,
            @Context HttpServletRequest request){
        System.out.println("inside viewUserHome for id:"+id);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("current user:"+user);
        System.out.println("message:"+message);
        if(message != null){
            message ="User not found";
            session.setAttribute("message", message);            
        }
        
        if(user != null && id.equals(user.getId())){
            System.out.println("view is:"+PREFIX+"user"+SUFFIX);
            return  PREFIX+"user"+SUFFIX;  
        }
        else{
            message ="User not found";
            session.setAttribute("message", message);
            return "redirect:/Events/list";
        }
    }

    //@RequestMapping(value="/Events/likeEvent/{userId}/{eventId}", method = RequestMethod.GET)
    @GET
    @Path("likeEvent/{userId}/{eventId}")
    public String likeEvent(
            @PathParam ("userId") Long userId, 
            @PathParam("eventId") Long eventId,
            @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println("inside likeEvent");
        User user = (User) session.getAttribute("user");
        System.out.println("userId:"+userId);
        System.out.println("current user:"+user);
        if(user != null && user.getId().equals(userId)){
            Event event = eventDAO.findById(eventId);
            System.out.println("select event:"+event);
            if(event != null){
                if(userDAO.likeEvent(user, event)){
                    session.removeAttribute("user");
                    session.setAttribute("user", user);
                    
                    session.setAttribute("message", "event:"+event.getTitle()+" added to interested list");
                }
            }else{
               session.setAttribute("message", "event is not exist");
            }
        }
        else{
            session.setAttribute("message","login first" );
        }
        return "redirect:/Events/userHome/"+userId;
    }

    //@RequestMapping(value="/Events/unlikeEvent/{userId}/{eventId}", method = RequestMethod.GET)
    @GET
    @Path("unlikeEvent/{userId}/{eventId}")
    public String unlikeEvent(
            @PathParam ("userId") Long userId, 
            @PathParam("eventId") Long eventId,
            @Context HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println("inside unlikeEvent");
        User user = (User) session.getAttribute("user");
        System.out.println("userId:"+userId);
        System.out.println("current user:"+user);
        if(user != null && user.getId().equals(userId)){
            Event event = eventDAO.findById(eventId);
            System.out.println("select event:"+event);
            if(event != null){
                if(userDAO.unlikeEvent(user, event)){
                    session.removeAttribute("user");
                    session.setAttribute("user", user);
                    session.setAttribute("message", "event:"+event.getTitle()+" remved from interested list");
                }
            }else{
               session.setAttribute("message", "event is not exist");
            }
        }
        else{
            session.setAttribute("message","login first" );
        }        
        return "redirect:/Events/userHome/"+userId;
    }
}
