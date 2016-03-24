<%-- 
    Document   : EventView
    Created on : Feb 16, 2016, 9:58:50 AM
    Author     : a
--%>

<%@include file="../top.jspf" %>

<%
    Event event = (Event)request.getAttribute("event");
    if(event == null) return;
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Event View</title>
    </head>
    <body>
        <c:if test="${user != null}" >
            <c:url var="createEventUrl" value="Events">
                <c:param name='action' value="create" /> 
            </c:url>
            <a href="${createEventUrl}"><c:out value="create event" /> </a><br><br>
        </c:if>
        <c:if test="${user == null}" >
            <c:url var="listEventsUrl" value="Events">
                <c:param name='action' value="list" /> 
            </c:url>
            <a href="${listEventsUrl}"><c:out value="Events List" /> </a><br><br>
        </c:if>
                
        <h3>Event View</h3>
            
            
        <span class="label">Title</span> <br/>       
        <input type="hidden" name='title' id='title' value="${event.getTitle()}"/>
        <c:out  value="${event.getTitle()}"/><br/><br/>
        
        <span class="label">Description</span><br/>
        <input type="hidden" name='description' id='description' value="${event.getDescription()}"/>
        <c:out value="${event.getDescription()}"/><br/><br/>
            
        <span class="label">Time</span><br/>
        <input type="hidden" name='dateTime' id='dateTime' value="${event.getTimeStr()}"/>
        <c:out value="${event.getTimeStr()}"/><br/><br/>
            
        <span class="label">Location</span><br/>
        <input type="hidden" name='location' id='location' value="${event.getLocation()}"/>
        <c:out value="${event.getLocation()}"/><br/><br/>
        <hr>
        <div id="map" style="width:500px;height:380px;" ></div>

            
        <script src="public/jquery-2.0.3.min.js"></script>
        <script src="public/gmaps.js" type="text/javascript"></script>
        <script src="public/maps-googleapis.js" type="text/javascript"></script>
        <script src="public/EventList.js" type="text/javascript"></script>
        <script src="public/top.js"></script>
    </body>
</html>
