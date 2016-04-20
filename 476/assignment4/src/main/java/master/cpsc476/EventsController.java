/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master.cpsc476;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import master.cpsc476.dao.*;
import master.cpsc476.forms.EventDetail;
import master.cpsc476.forms.LoginDetail;
import master.cpsc476.forms.SignupDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Mussa
 */
@Controller
public class EventsController {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    private EventDAO eventDAO;
    private UserDAO userDAO;
       
    @PostConstruct
    public void init(){
        System.out.println("inside PostConstruct init");
        
        eventDAO = (EventDAO) applicationContext.getBean("EventDAOImp");
        userDAO = (UserDAO) applicationContext.getBean("UserDAOImp");
        
        /*User user = new User(null, "ali", "ali@gmail.com");
        user.setPassword("a");
        user = userDAO.createUser(user);
        System.out.println("@@@create user:"+user);
        user = userDAO.findByEmail("ali@gmail.com");
        System.out.println("@@@find user:"+user);
        
        Event event = new Event();
        event.setTitle("Summit Event Productions");
        event.setDescription("We are a boutique lighting design production company based in the MD/DC/VA area");
        event.setLocation("2627 e la palma ave");
        event.setEventTime(LocalDateTime.now().plusDays(7l));
        event.setCreatedBy(user);
        event = eventDAO.createEvent(event);
        System.out.println("@@@create event:"+event);
        System.out.println("@@@findAll event:"+eventDAO.findAll());
        //System.out.println("@@@like result:"+userDAO.likeEvent(user, event)); */
    }

    @RequestMapping(value={"/Events/list","/Events/","/Events"}, method = RequestMethod.GET)
    public String listEvents(@RequestParam(value="message", required = false) String message,Model model){
        List<Event> eventsList = eventDAO.findAll();
        System.out.println("inside listEvents eventsCollection :"+eventsList);
        
        model.addAttribute("loginDetail", new LoginDetail());
        model.addAttribute("eventsCollection", eventsList);
        if(message != null){
            model.addAttribute("message", message);
        }
        return "EventsList";
    }

    @RequestMapping(value="/Events/view/{id}", method = RequestMethod.GET)
    public String viewEvent(@PathVariable Long id, Model model, HttpSession session){
        System.out.println("id:"+id);
        if(id == null){
            return "redirect:/Events/list";
        }
        Event event = eventDAO.findById(id);
        
        if(event == null){
                return "redirect:/Events/list";
        }
        if(session.getAttribute("user") == null){
            model.addAttribute("loginDetail", new LoginDetail());
        }
        model.addAttribute("event", event);
        return "EventView";
    }
    
    @RequestMapping(value="/Events/create", method = RequestMethod.GET)
    public String showEventForm(Model model)
    {
        System.out.print("in /create before redirct");
        LocalDate today = LocalDate.now();
        LocalDate nextYeer = today.plus(1,ChronoUnit.YEARS);
        System.out.print("today:"+today);
        System.out.print("nextYeer:"+nextYeer);
        model.addAttribute("minDate", today);
        model.addAttribute("maxDate", nextYeer);
        model.addAttribute("eventDetail", new EventDetail());
        return "EventForm";
    }


    @RequestMapping(value="/Events/createEvent", method = RequestMethod.POST)
    public String createEvent(@ModelAttribute("eventDetail") EventDetail eventDetail, 
            @RequestParam(value="sourceJsp", required = false) String sourceJsp,
            BindingResult result, ModelMap model, HttpSession session){
        System.out.println("inside createEvent");
        User user = (User)session.getAttribute("user");
        System.out.println("inside createEvent user:"+user);
        Event event = new Event(eventDetail);
        
        event.setCreatedBy(user);
        event = eventDAO.createEvent(event);
        if(event == null){
            model.addAttribute("message", "ERROR: can't create events");
            return "EventForm";
        }
        model.addAttribute("message", "successfull created event:"+event.getTitle());
        //not add but recall getCreated
        /*
            simple add event to user ctreated list is produce unsorted list
            user.getCreatedEvents().add(event);

        */
        user.setCreatedEvents(eventDAO
                .findEventsCreatedByUser(user));

        System.out.println("user afetr find event created:"+user);
        
        session.removeAttribute("user");
        session.setAttribute("user", user);
        return "redirect:/Events/view/" + event.getId();
    }
    
    @RequestMapping(value="/Events/createUser", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("signupDetail") SignupDetail signupDetail, 
            @RequestParam(value="sourceJsp", required = false) String sourceJsp,
            BindingResult result, ModelMap model, HttpSession session){
        
        if (result.hasErrors()) {
            System.out.println("signin result has error");
            model.addAttribute("message", result.getFieldError().toString());
            model.addAttribute("signupDetail",signupDetail);
            return "SignupForm";
        }
        User user = userDAO.findByEmail(signupDetail.getEmail());
        if(user != null){
            System.out.println("error in create user:"+user.getEmail()+" . Email already exist");
            model.addAttribute("message","user already exist");
            model.addAttribute("signupDetail",signupDetail);
            return "SignupForm";
        }
        user = new User(signupDetail);
        
        //TODO remove this
        //user.setInterestedEvents(new ArrayList<Event>());
        //user.setCreatedEvents(new ArrayList<Event>());
        System.out.println("will create user:"+user.toString());
        user = userDAO.createUser(user);
        if(user == null){
            model.addAttribute("message", "ERROR: can't create user");
            if(sourceJsp == null){
                return "redirect:/Events/list";
            }else{
                return sourceJsp;
            }
        }
        System.out.println("create user:"+user.toString());
        /*
        Event event = new Event();
        event.setTitle("Summit Event Productions");
        event.setDescription("We are a boutique lighting design production company based in the MD/DC/VA area");
        event.setLocation("2627 e la palma ave");
        event.setEventTime(LocalDateTime.now().plusDays(7l));
        event.setCreatedBy(user);
        event = eventDAO.createEvent(event);
        System.out.println("create event:"+event);
        
        userDAO.likeEvent(user, event);
        
        
        System.out.println("test find by id user:"+userDAO.findById(user.getId()));
        */
        session.setAttribute("user", user);
        return"redirect:/Events/list";
    }
    

    @RequestMapping(value={"/Events/signup*"}, method = RequestMethod.GET)
    public String showSignupForm(
            @RequestParam(value="email", required = false) String email,Model model){
            System.out.println("signup for :"+email);
            SignupDetail signupDetail = new SignupDetail();
            if(email != null)
                signupDetail.setEmail(email);
            model.addAttribute("signupDetail", signupDetail);
            return "SignupForm";    
    }

    @RequestMapping(value="/Events/signin", method = RequestMethod.POST)
    public String signin(@ModelAttribute("loginDetail") LoginDetail loginDetail, 
            @RequestParam(value="sourceJsp", required = false) String sourceJsp,
            BindingResult result, ModelMap model, HttpSession session) {
           
        if (result.hasErrors()) {
            System.out.println("signin result has error");
            model.addAttribute("message", result.getFieldError().toString());
            return "redirect:/Events/list";
        }
        String email = loginDetail.getEmail();
        String password = loginDetail.getPassword();
        System.out.println("signin for :"+email);
        System.out.println("signin for :"+password);
        
        User user = userDAO.isMatch(email, password);
        System.out.println("isMatch:"+user);
            
        if(user != null){           
            user.setInterestedEvents(eventDAO
                    .findEventsInterestedByUser(user));
            user.setCreatedEvents(eventDAO
                    .findEventsCreatedByUser(user));
            
            session.setAttribute("user", user);
            return "redirect:/Events/userHome/"+user.getId();
            
        }else{
            model.addAttribute("message", "signin fail");
            System.out.println("sourceJSP:"+sourceJsp);
            
            if(sourceJsp == null){
                return "redirect:/Events/list";
            }else{
                return sourceJsp;
            }
        }
    }

    @RequestMapping(value="/Events/signout", method = RequestMethod.GET)
    public String signout(Model model, HttpSession session) {
        
        session.removeAttribute("user");
        model.addAttribute("message","user successfully signout " );
        //model.addAttribute("loginDetail", new LoginDetail());
            
        return "redirect:/Events/list";

    }

    @RequestMapping(value="/Events/userHome/{id}", method = RequestMethod.GET)
    public String viewUserHome(@PathVariable Long id, 
            @RequestParam(value="message", required = false) String message,
            Model model, HttpSession session){
        System.out.println("inside viewUserHome for id:"+id);
        User user = (User) session.getAttribute("user");
        System.out.println("current user:"+user);
        if(message != null){
            model.addAttribute("message", message);
        }
        
        if(user != null && user.getId().equals(id)){
            return "user"; 
        }
        else{
            model.addAttribute("message","User not found" );
            return "redirect:/Events/list";
        }
    }

    @RequestMapping(value="/Events/likeEvent/{userId}/{eventId}", method = RequestMethod.GET)
    public String likeEvent(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId,
            Model model, HttpSession session){
        System.out.println("inside likeEvent");
        User user = (User) session.getAttribute("user");
        System.out.println("userId:"+userId);
        System.out.println("current user:"+user);
        if(user != null && user.getId().equals(userId)){
            Event event = eventDAO.findById(eventId);
            System.out.println("select event:"+event);
            if(event != null){
                if(userDAO.likeEvent(user, event)){
                    if(user.getInterestedEvents() == null){
                        user.setInterestedEvents(new ArrayList<>()); 
                    }
                    user.getInterestedEvents().add(event);
                    user.getInterestedEvents().sort((e1, e2) -> e1.compareTo(e2));
                    session.removeAttribute("user");
                    session.setAttribute("user", user);
                    model.addAttribute("message", "event:"+event.getTitle()+" added to interested list");
                }
            }else{
               model.addAttribute("message", "event is not exist");
            }
        }
        else{
            model.addAttribute("message","login first" );
        }
        return "redirect:/Events/userHome/"+userId;
    }

    @RequestMapping(value="/Events/unlikeEvent/{userId}/{eventId}", method = RequestMethod.GET)
    public String unlikeEvent(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId,
            Model model, HttpSession session){
        System.out.println("inside unlikeEvent");
        User user = (User) session.getAttribute("user");
        System.out.println("userId:"+userId);
        System.out.println("current user:"+user);
        if(user != null && user.getId().equals(userId)){
            Event event = eventDAO.findById(eventId);
            System.out.println("select event:"+event);
            if(event != null){
                if(userDAO.unlikeEvent(user, event)){
                    user.getInterestedEvents().remove(event);
                    session.removeAttribute("user");
                    session.setAttribute("user", user);
                    model.addAttribute("message", "event:"+event.getTitle()+" remved from interested list");
                }
            }else{
               model.addAttribute("message", "event is not exist");
            }
        }
        else{
            model.addAttribute("message","login first" );
        }        
        return "redirect:/Events/userHome/"+userId;
    }
    
}
