<%-- 
    Document   : EventForm
    Created on : Feb 16, 2016, 9:58:00 AM
    Author     : Mussa
--%>
<%@include file="../top.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Event Creation Form &quot;</title>
        
    </head>
    <body>
        <h3>Create New Event</h3>
        <form id="createEventForm" method="POST" action="Events">
            <input type="hidden" name="action" value="createEvent"/>
            <span class="label">Title</span><br/>
            <input type="text" name="title" id="title" placeholder="Event Title" 
                   required title="length must be four or more"/><br/><br/>
            <span class="label">Description</span><br/>
            <textarea  name="description" id="description" placeholder="Event Description" 
                rows="4" cols="50" required  ></textarea><br/><br/>
            <span class="label">Time</span><br/>
            
           <!-- <input type="datetime-local" id="eventDateTime"  name="eventDateTime" required
                     min="2016-02-06T12:12:55" max="2016-02-16T21:25:33" readonly> -->

            <input type="datetime-local" name="eventDateTime" id="eventDateTime" required 
                   min="${minDate}" max="${maxDate}"><br/><br/>
            <span class="label">Location</span><br/>
            <input type="text" name="location" id="location"  size="50"
                   placeholder="Location" required
                   onblur="checkGeocode();"/>
            <a onclick="checkGeocode();" href="javascript:void(0);">show</a> <br/><br/>
<br>
            <input type="submit" id="submitBtn" value="Create"/>
        </form>
        <hr>
                <div id="map" style="width:300px;height:280px;"></div>

        <script src="public/jquery-2.0.3.min.js"></script>
        <script src="public/jquery-ui-git.js"></script>
        <script src="public/gmaps.js" type="text/javascript"></script>
        <script src="public/maps-googleapis.js" type="text/javascript"></script>
        <script src="public/EventForm.js"></script>
        <script src="public/top.js"></script>
     
    </body>
</html>
