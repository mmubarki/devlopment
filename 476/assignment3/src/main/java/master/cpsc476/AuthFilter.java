package master.cpsc476;


import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Stream;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import master.cpsc476.User;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

/**
 *
 * @author Mussa
 */
@WebFilter(filterName = "AuthFilter", servletNames = {"EventsDispatcher"}, dispatcherTypes = {DispatcherType.REQUEST})
public class AuthFilter implements Filter {
    
    private FilterConfig filterConfig = null;
    
    public AuthFilter() {
    }    


    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("req.getRequestURI():"+req.getRequestURI());
        System.out.println("req.getContextPath():"+req.getContextPath());
        
        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("path without context:"+path);
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
        System.out.println("inside filter: doFilter");
        System.out.println("path:"+path);
        System.out.println("action:"+action);
        
        //String action = request.getParameter("action");
        if(Stream.of("create","createEvent","userHome","likeEvent","unlikeEvent").anyMatch(e -> e.equals(action))){
            HttpSession session = ((HttpServletRequest) request).getSession();
            User user = (User) session.getAttribute("user");
            if(user == null){
                String sourceJsp = request.getParameter("sourceJsp");
                if(sourceJsp == null){
                    sourceJsp = "/list";
                }else{
                    sourceJsp = "/WEB-INF/jsp/"+sourceJsp+".jsp";
                }
                System.out.println("sourceJsp:"+sourceJsp);
                session.setAttribute("message","in filter action:"+action+" message: login first" );
                ((HttpServletRequest) request).
                        getRequestDispatcher(sourceJsp).
                        forward(request, response);
            }else{
                chain.doFilter(request, response);        
            }
        }
        else{
           chain.doFilter(request, response);   
        }
        
        
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
}
