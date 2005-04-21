<%@ include file="/taglibs.jsp"%> 
<title>Springbreak AddFeed</title> 
<p>Fill in Channel Info: </p> 


<!--
Roland Vecera
Input für FeedSubscribtion


TODO: was macht onsubmit="return validateChannel(this)
-->

<form name="channel" method="post" action="<c:url value="/addChannel.html"/>" > 
	<table> 
		
		<tr> 
			<th>
				URL: 
			</th> 
			<td> 
				
				<spring:bind path="channel.locationString">
	               <input type="text" name="locationString" value="URL here"/>
	              
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
		<td><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
			
	</table> 
</form> 
<content tag="underground">

<strong><fmt:message key="common.newsagg"/></strong>
</content>
