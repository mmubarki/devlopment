<%-- 
    Document   : SignupForm
    Created on : Feb 16, 2016, 3:05:13 PM
    Author     : Mussa
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/public/events.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Signup Form</title>
    </head>
    <body>
        <h3><a href= "list"> Home </a>    </h3>
        <h3>Signup Form</h3>
        <form:form modelAttribute="signupDetail" method="POST" action="${pageContext.request.contextPath}/Events/createUser" name="createUserForm">
     

            <input type="hidden" name="sourceJsp" value="SignupForm"/>
            <span class="label">Your Name</span><br/>
            <form:input type="text" path="name" placeholder="Your Name" required="true"/><br/><br/>
            <span class="label">email</span><br/>
            <form:input type="email" path="email" placeholder="Email Id" 
                   required="true" value='${email}'/><br/><br/>
            <span class="label">Password</span><br/>
            <form:input type="password" path="password" id="password" placeholder="Password" 
                   required="true" pattern=".{6,}" title="length must be six or more"/><br/><br/>
            <span class="label">Confirm Password</span><br/>
            <input type="password" name="confirmPassword" id="confirmPassword" 
                   placeholder="Confirm Password" required/><br/><br/>
            <input type="submit" id="submitBtn" value="Submit"/>
            <a href= "list"> Cancel </a>
        </form:form>
           <br><br>
<c:if test="${message != null}">
    <span class="error">${message}</span>
</c:if>
        <script src="${pageContext.request.contextPath}/public/jquery-2.0.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/public/SignupForm.js"></script>
        <script src="${pageContext.request.contextPath}/public/top.js"></script>
    </body>
</html>
