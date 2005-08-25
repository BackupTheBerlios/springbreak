<!-- 
Roland Vecera
22. August 2005

Use this .jsp to display a collection of Items


-->
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<script type="text/javascript" src="./fat.js"></script>
<script type="text/javascript" src="./ajaxcore.js"></script>
 



<c:forEach var="i" items="${items}" varStatus="status"> 
      <div class="item<c:out value='${status.count % 2}'/>">
      <table>
      	<tr>
      		<td><c:out value="${i.title}"/> </td> <td><c:out value="${i.date}"/></td>
      	</tr>
      	<tr>
      		<td colspan="2"><c:out value="${i.description}"/></td>
      	</tr>
      	
      	<tr>
      		<td colspan="2">
      			


  <c:choose>
<c:when test='${i.read == true}'>
<div>
<img onmouseover="this.style.cursor='pointer'" align="top" id="watched${i.id}" src="./images/watched_y.gif" />
</c:when>
<c:otherwise>
<div <c:if test='${status.count < 10}'>id="paragraph${i.id}" class="fade" </c:if> >
	<img onmouseover="this.style.cursor='pointer'" align="top" id="watched${i.id}" src="./images/watched_n.gif" />
</c:otherwise>
</c:choose>	
  Toggle Me
</div>

<div id="watchedResponseContainer${i.id}">
  Response
  <div id="watchedResponse${i.id}"><br/></span>
</div>
<form>
<c:choose>
<c:when test='${i.read == true}'>
	<input type="hidden" id="watchedStatus${i.id}" name="watchedStatus" value="true"/>
</c:when>
<c:otherwise>
	<input type="hidden" id="watchedStatus${i.id}" name="watchedStatus" value="false"/>
</c:otherwise>
</c:choose>	
</form>
 <ajax:toggle
 		 imageId="watched${i.id}"
 		 targetId="watchedResponse${i.id}"
  		 stateId="watchedStatus${i.id}"
 		 baseUrl="./readFeedItem.html?id=${i.id}"
  		 paramName="state"
  		 imageOn="./images/watched_y.gif"
  		 imageOff="./images/watched_n.gif"
  		  />     		
    		
      		
      		
      		</td>
      	</tr>
      	
      	
      </table>
      </div>
      
      

	
    		
</c:forEach> 