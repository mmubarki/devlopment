<%-- 
    Document   : SignupForm
    Created on : Feb 16, 2016, 3:05:13 PM
    Author     : Mussa
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="public/events.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Signup Form</title>
    </head>
    <body>
        <h3><a href= "Events?action=list"> Home </a>    </h3>
        <h3>Signup Form</h3>
        <form id="createUserForm" method="POST" action="Events">
            <input type="hidden" name="action" value="createUser"/>
            <input type="hidden" name="sourceJsp" value="SignupForm.jsp"/>
            <span class="label">Your Name</span><br/>
            <input type="text" name="name" placeholder="Your Name" required/><br/><br/>
            <span class="label">email</span><br/>
            <input type="email" name="email" placeholder="Email Id" 
                   required value='${email}'/><br/><br/>
            <span class="label">Password</span><br/>
            <input type="password" name="password" id="password" placeholder="Password" 
                   required pattern=".{6,}" title="length must be six or more"/><br/><br/>
            <span class="label">Confirm Password</span><br/>
            <input type="password" name="confirmPassword" id="confirmPassword" 
                   placeholder="Confirm Password" required/><br/><br/>
            <input type="submit" id="submitBtn" value="Submit"/>
            <a href= "Events?action=list"> Cancel </a>
        </form>
           <br><br>
<c:if test="${sessionScope.message != null}">
    <span class="error">${message}</span>
    <c:remove var="message" scope="session"/>
</c:if>
        <script src="public/jquery-2.0.3.min.js"></script>
        <script src="public/SignupForm.js"></script>
        <script src="public/top.js"></script>
    </body>
</html>
