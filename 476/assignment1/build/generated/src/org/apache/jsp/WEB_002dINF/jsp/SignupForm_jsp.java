package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class SignupForm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Signup Form</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h3><a href= \"Events?action=list\"> back to events </a>    </h3>\n");
      out.write("        <h3>Signup Form</h3>\n");
      out.write("        <form id=\"createUserForm\" method=\"POST\" action=\"Events\">\n");
      out.write("            <input type=\"hidden\" name=\"action\" value=\"createUser\"/>\n");
      out.write("            <span class=\"label\">Your Name</span><br/>\n");
      out.write("            <input type=\"text\" name=\"name\" placeholder=\"Your Name\" required=\"true\"/><br/><br/>\n");
      out.write("            <span class=\"label\">email</span><br/>\n");
      out.write("            <input type=\"email\" name=\"email\" placeholder=\"Email Id\" required=\"true\"/><br/><br/>\n");
      out.write("            <span class=\"label\">Password</span><br/>\n");
      out.write("            <input type=\"password\" name=\"password\" id=\"password\" placeholder=\"Password\" \n");
      out.write("                   required=\"true\" pattern=\".{6,}\" title=\"length must be six or more\"/><br/><br/>\n");
      out.write("            <span class=\"label\">Confirm Password</span><br/>\n");
      out.write("            <input type=\"password\" name=\"confirmPassword\" id=\"confirmPassword\" \n");
      out.write("                   placeholder=\"Confirm Password\" required=\"true\"/><br/><br/>\n");
      out.write("            \n");
      out.write("            <input type=\"submit\" id=\"submitBtn\" value=\"Submit\"/>\n");
      out.write("        </form>\n");
      out.write("        \n");
      out.write("        <script src=\"public/jquery-2.0.3.min.js\"></script>\n");
      out.write("        <script src=\"public/SignupForm.js\"></script>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
