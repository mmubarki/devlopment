<%-- 
    Document   : user
    Created on : Feb 27, 2016, 8:14:43 AM
    Author     : Mussa
--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Detail</title>
    </head>
    <body>
        <%@include file="../top.jspf" %>

        <h4>User Detail:</h4>
        Name: ${user.getName()}<br>
        Name: ${user.getEmail()}<br>
        
        <h4>Interested Events</h4> <br>
        <c:choose>
            <c:when test="${user.getInterestedEvents() != null && 
                            user.getInterestedEvents().size() > 0}">
                <ul>
                <c:forEach items="${user.getInterestedEvents()}" var="event">
                    <c:url var="viewEventUrl" value='/Events/view/${event.getId()}'/>
                    <li> <a href='${viewEventUrl}'> 
                            <c:out value="${event.getTitle()}"/> 
                            in : <c:out value="${event.getEventTimeStr()}"/>               
                            at : <c:out value="${event.getLocation()}"/>
                        </a> 
                        <c:url var="unlikeEventUrl" value='/Events/unlikeEvent/${user.getId()}/${event.getId()}'/>
                        
                   &ensp;&ensp;<a href="${unlikeEventUrl}">
                                <img src="${pageContext.request.contextPath}/public/unlike.png" alt="Unlike" width="15" height="15"/>
                         </a>
                   <c:url var="addToCalendarUrl" value="http://www.google.com/calendar/event">
                       <c:param name="action" value="TEMPLATE"/>
                       <c:param name="text" ><c:out value="${event.getTitle()}"/></c:param>
                       <c:param name="details" ><c:out value="${event.getDescription()}"/></c:param>
                       <c:param name="location" ><c:out value="${event.getLocation()}"/></c:param>
                       <c:param name="dates" ><c:out value='${event.getEventTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}/${event.getEventTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}'/></c:param> 
                  </c:url>
                   &ensp;&ensp; <a href="${addToCalendarUrl}" target="_blank">
                            <img src="${pageContext.request.contextPath}/public/googleCalendar.png" alt="add to google calendar" width="20" height="20"/>
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
                           value='/Events/view/${event.getId()}'/>
                    <li>  <a href='${viewEventUrl}'> 
                            <c:out value="${event.getTitle()}"/> 
                            in : <c:out value="${event.getEventTimeStr()}"/>               
                            at : <c:out value="${event.getLocation()}"/>
                        </a> 
                        <c:url var="addToCalendarUrl" value="http://www.google.com/calendar/event">
                       <c:param name="action" value="TEMPLATE"/>
                       <c:param name="text" ><c:out value="${event.getTitle()}"/></c:param>
                       <c:param name="details" ><c:out value="${event.getDescription()}"/></c:param>
                       <c:param name="location" ><c:out value="${event.getLocation()}"/></c:param>
                       <c:param name="dates" >
                           <c:out value='${event.getEventTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}/${event.getEventTime().format( DateTimeFormatter.ofPattern("yyyyMMdd\'T\'HHmmss"))}'/></c:param> 
                  </c:url>
                   &ensp;&ensp; <a href="${addToCalendarUrl}" target="_blank">
                            <img src="${pageContext.request.contextPath}/public/googleCalendar.png" alt="add to google calendar" width="20" height="20"/>
                        </a>
                    </li>
                </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                You are not create any events
            </c:otherwise>
        </c:choose>
        
        <c:url var="createEventUrl" value="/Events/create"/>
        <a href="${createEventUrl}">
            <c:out value="create new event" /> 
        </a>
        <br><br>
        
    </body>
</html>
