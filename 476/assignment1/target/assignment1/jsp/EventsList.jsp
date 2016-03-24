<%-- 
    Document   : EventsList
    Created on : Feb 16, 2016, 10:12:01 AM
    Author     : Mussa
--%>
<%@include file="../top.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Events List</title>
    </head>
    <body>

        <h3>Events List</h3>
        <c:if test="${user != null}" >
            <c:url var="createEventUrl" value="Events">
                <c:param name='action' value="create" /> 
            </c:url>
            <a href="${createEventUrl}"><c:out value="create event" /> </a><br><br>
        </c:if>
        <c:choose>
            <c:when test="${requestScope.eventsCollection != null}">                            
                <table border='0'>
                    <thead class="Header">
                        <tr>
                            <th>Title</th>
                            <th>Time</th>
                            <th>Location</th>
                            <th> </th>
                        </tr>
                    </thead>
                
                <c:forEach items="${eventsCollection}" var="event">
                    <tr>
                        <td>
                            <c:url var="viewEventUrl" value='Events'>
                                <c:param name="action" value="view"/>
                                <c:param name="eventId" value="${event.value.getId()}"/>
                            </c:url>
                            <a href='${viewEventUrl}'> 
                            <c:out value="${event.value.getTitle()}"/>                
                            </a> 
                            <input type="hidden" name='title' id='title' value="${event.value.getTitle()}"/>
                            <input type="hidden" name='description' id='description' value="${event.value.getDescription()}"/>
                          </td>
                          <td>&ensp;&ensp;<c:out value="${event.value.getTimeStr()}"/>
                              
                            <input type="hidden" name='dateTime' id='dateTime' value="${event.value.getTimeStr()}"/>
                          </td>
                          <td>&ensp;&ensp;<c:out value="${event.value.getLocation()}"/>
                              <input type="hidden" name='location' id='location' value="${event.value.getLocation()}"/>
                          </td>
                            <td>
                            <c:if test="${user != null &&
                                          user.getInterestedEvents() != null &&
                                           !user.getInterestedEvents().contains(event.value)}">
                                
                                <c:url var="likeEventUrl" value='Events'>
                                    <c:param name="action" value="likeEvent"/>
                                    <c:param name="userId" value="${user.getId()}"/>
                                    <c:param name="eventId" value="${event.value.getId()}"/>
                                </c:url>
                                &ensp;&ensp;
                                <a href='${likeEventUrl}'> 
                                    <img src="public/like.png" alt="Like" width="15" height="15"/>
                                </a>                                     
                              </c:if>
                              </td>
                    </tr> 
                </c:forEach>
                </table>
                <hr>
                <div id="map" style="width:500px;height:380px;"></div>
            </c:when>
            <c:otherwise>
                there are no coming events
            </c:otherwise>
        </c:choose>                

        <script src="public/jquery-2.0.3.min.js"></script>
        <script src="public/gmaps.js" type="text/javascript"></script>
        <script src="public/maps-googleapis.js" type="text/javascript"></script>
        <script src="public/EventList.js" type="text/javascript"></script>
        <script src="public/top.js"></script>
        
    </body>
</html>
