<%-- 
    Document   : EventsList
    Created on : Feb 16, 2016, 10:12:01 AM
    Author     : Mussa
--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Events List</title>
    </head>
    <body>
        <%@include file="../top.jspf" %>

        <h3>Events List</h3>
        <c:if test="${user != null}" >
            <c:url var="createEventUrl" value="/Events/create"/>
            <a href="${createEventUrl}"><c:out value="create event" /> </a><br><br>
        </c:if>
        <c:choose>
            <c:when test="${eventsCollection != null &&
                            eventsCollection.size()>0}">                            
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
                            <c:url var="viewEventUrl" value='/Events/view/${event.getId()}'/>
                            <a href='${viewEventUrl}'> 
                            <c:out value="${event.getTitle()}"/>                
                            </a> 
                            <input type="hidden" name='title' id='title' value="${event.getTitle()}"/>
                            <input type="hidden" name='description' id='description' value="${event.getDescription()}"/>
                          </td>
                          <td>&ensp;&ensp;<c:out value="${event.getEventTimeStr()}"/>
                              
                            <input type="hidden" name='dateTime' id='dateTime' value="${event.getEventTimeStr()}"/>
                          </td>
                          <td>&ensp;&ensp;<c:out value="${event.getLocation()}"/>
                              <input type="hidden" name='location' id='location' value="${event.getLocation()}"/>
                          </td>
                            <td>
                            <c:if test="${user != null && (user.getInterestedEvents() == null ||
                                          (user.getInterestedEvents() != null &&
                                           !user.getInterestedEvents().contains(event)))}">
                                
                                <c:url var="likeEventUrl" 
                                       value='/Events/likeEvent/${user.getId()}/${event.getId()}'/>
                                &ensp;&ensp;
                                <a href='${likeEventUrl}'> 
                                    <img src="${pageContext.request.contextPath}/public/like.png" alt="Like" width="15" height="15"/>
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
        <script src="${pageContext.request.contextPath}/public/jquery-2.0.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/public/gmaps.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/public/maps-googleapis.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/public/EventList.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/public/top.js"></script>
        
    </body>
</html>
