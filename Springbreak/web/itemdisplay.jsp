
	<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
	<script type="text/javascript" src="./fat.js"></script>
	<script type="text/javascript" src="./ajaxcore.js"></script>




<c:forEach var="i" items="${items}" varStatus="status"> 
      <div class="item<c:out value='${status.count % 2}'/>">
      <table style="width:100%">
     
			
      	<tr <c:if test='${(i.read != true) && (status.count < 10)}'> id="tr${i.id}" class="fade" </c:if> >
      		<td><a class="itemtitle" href="${i.link}" target="blank">${i.title}</a> </td> 
      	</tr>
      	<tr>
      		<td>Posted on: ${i.date} by ${i.creator} on <a class="channellocation" href="${i.channel.siteString}" target="blank">${i.channel.title}</a></td>
      	</tr>
      	<tr>
      		<td colspan="2"><div class="description"> <c:out value="${i.description}"/></div></td>
      	</tr>
      	
      	<tr>
      		<td colspan="2">
      			


				<c:choose>
					<c:when test='${i.read == true}'>
						<div>
							Read Status:
							<img onmouseover="this.style.cursor='pointer'"  id="watched${i.id}" src="./images/watched_y.gif" />
					</c:when>
					<c:otherwise>
						<div>
							Read Status:
							<img onmouseover="this.style.cursor='pointer'"  id="watched${i.id}" src="./images/watched_n.gif" />
					</c:otherwise>
				</c:choose>	
  				<span id="watchedResponse${i.id}"> </span>
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
				<c:url var="readURL" value="./readFeedItem.html">
					<c:param name="id" value="${i.id}" />
				</c:url>
 				<ajax:toggle
 					 imageId="watched${i.id}"
 					 targetId="watchedResponse${i.id}"
  					 stateId="watchedStatus${i.id}"
 					 baseUrl="${readURL}"
  					 paramName="state"
  					 imageOn="./images/watched_y.gif"
  					 imageOff="./images/watched_n.gif"
  		  		/>     		
    		
      		
      		
      		</td>
      	</tr>
      	
      	
      </table>
      </div>
      
      

	
    		
</c:forEach> 