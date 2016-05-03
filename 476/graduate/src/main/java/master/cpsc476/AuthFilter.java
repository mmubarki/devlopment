package master.cpsc476;


import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import master.cpsc476.User;

/**
 *
 * @author Mussa
 */
//@WebFilter(filterName = "AuthFilter", servletNames = {"EventsDispatcher"}, dispatcherTypes = {DispatcherType.REQUEST})
@Provider
@Priority(Priorities.AUTHENTICATION)

public class AuthFilter implements ContainerRequestFilter {
    
    @Context
    HttpServletRequest req;               
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        System.out.println("req.getRequestURI():"+req.getRequestURI());
        System.out.println("req.getContextPath():"+req.getContextPath());
        
        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("path without context:"+path);
        path = path.replace("/MVC", "") ; // lookinf for action in uri /Events/{action}/...
        path = path.replace("/Events", "") ; // lookinf for action in uri /Events/{action}/...
        int i = path.indexOf("/",1);//look up "/" 
        if(i >0){ // action in uri
          path = path.substring(1,i); 
        }else if(path.length() > 0){ // set yhe default action=list
          path = path.substring(1); 
        }else {
            path="list";
        }
        String action  = path;
        path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("inside filter: doFilter");
        System.out.println("path:"+path);
        System.out.println("action:"+action);
        
        //String action = request.getParameter("action");
        if(Stream.of("create","createEvent","userHome","likeEvent","unlikeEvent").anyMatch(e -> e.equals(action))){
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            System.out.println("in filter user:"+user);
                
            if(user == null){
                requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("message from Filter: User cannot access the resource.")
                    .build());
            }else{
                System.out.println("Authentication Filter Passed. User is "+user.getName());        
            }
        }
        else{
           System.out.println("action not require Authentication Filter");           
        }
        
        
    }

    

    
    
}
