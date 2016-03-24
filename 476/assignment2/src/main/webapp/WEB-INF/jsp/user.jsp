<%-- 
    Document   : user
    Created on : Feb 27, 2016, 8:14:43 AM
    Author     : Mussa
--%>
<%@include file="../top.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Detail</title>
    </head>
    <body>
        <h4>User Detail:</h4>
        Name: ${user.getName()}<br>
        Name: ${user.getEmail()}<br>
        
        <h4>Interested Events</h4> <br>
        <c:choose>
            <c:when test="${user.getInterestedEvents() != null && 
                            user.getInterestedEvents().size() > 0}">
                <ul>
                <c:forEach items="${user.getInterestedEvents()}" var="event">
                    <c:url var="viewEventUrl" value='Events'>
                        <c:param value="view" name="action"/>
                        <c:param value="${event.getId()}" name="eventId"/>
                    </c:url>
                    <li> <a href='${viewEventUrl}'> 
                            <c:out value="${event.getTitle()}"/> 
                            in : <c:out value="${event.getTimeStr()}"/>               
                            at : <c:out value="${event.getLocation()}"/>
                        </a> 
                        <c:url var="unlikeEventUrl" value='Events'>
                            <c:param value="unlikeEvent" name="action"/>
                            <c:param value="${event.getId()}" name="eventId"/>
                            <c:param name="userId" value="${user.getId()}"/>        
                        </c:url>
                   &ensp;&ensp;<a href="${unlikeEventUrl}">
                                <img src="public/unlike.png" alt="Unlike" width="15" height="15"/>
                         </a>
                   <c:url var="addToCalendarUrl" value="http://www.google.com/calendar/event">
                       <c:param name="action" value="TEMPLATE"/>
                       <c:param name="text" ><c:out value="${event.getTitle()}"/></c:param>
                       <c:param name="details" ><c:out value="${event.getDescription()}"/></c:param>
                       <c:param name="location" ><c:out value="${event.getLocation()}"/></c:param>
                       <c:param name="dates" ><c:out value='${event.getTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}/${event.getTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}'/></c:param> 
                  </c:url>
                   &ensp;&ensp; <a href="${addToCalendarUrl}" target="_blank">
                            <img src="public/googleCalendar.png" alt="add to google calendar" width="20" height="20"/>
                        </a>
                        
                    </li>
                </c:forEach>
            </ul>

            </c:when>
            <c:otherwise>
                You are not select any events.
            </c:otherwise>
        </c:choose>
        <h4>Mine Events</h4>
        <c:choose>
            <c:when test="${user.getCreatedEvents() != null}">
                <ul>
                <c:forEach items="${user.getCreatedEvents()}" var="event">
                    <c:url var="viewEventUrl" 
                           value='Events?action=view&eventId=${event.getId()}'/>
                    <li>  <a href='${viewEventUrl}'> 
                            <c:out value="${event.getTitle()}"/> 
                            in : <c:out value="${event.getTimeStr()}"/>               
                            at : <c:out value="${event.getLocation()}"/>
                        </a> 
                        <c:url var="addToCalendarUrl" value="http://www.google.com/calendar/event">
                       <c:param name="action" value="TEMPLATE"/>
                       <c:param name="text" ><c:out value="${event.getTitle()}"/></c:param>
                       <c:param name="details" ><c:out value="${event.getDescription()}"/></c:param>
                       <c:param name="location" ><c:out value="${event.getLocation()}"/></c:param>
                       <c:param name="dates" >
                           <c:out value='${event.getTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}/${event.getTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}'/></c:param> 
                  </c:url>
                   &ensp;&ensp; <a href="${addToCalendarUrl}" target="_blank">
                            <img src="public/googleCalendar.png" alt="add to google calendar" width="20" height="20"/>
                        </a>
                    </li>
                </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                You are not create any events
            </c:otherwise>
        </c:choose>
        
        <c:url var="createEventUrl" value="Events">
            <c:param name='action' value="create" /> 
        </c:url>
        <a href="${createEventUrl}">
            <c:out value="create new event" /> 
        </a>
        <br><br>
        
    </body>
</html>
