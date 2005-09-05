<%@ include file="/taglibs.jsp"%> 
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<script type="text/javascript" src="./ajaxcore.js"></script>

<title>Springbreak AddFeed</title> 
<p>
<b>Add a new RSS-channel to your menu!</b><br/><br/>

Insert a valid URL to the input field. 
<br/>Note: Don't forget to add a valid protocol like 'http://'
</p>


<!--
Roland Vecera
Input für FeedSubscribtion


TODO: was macht onsubmit="return validateChannel(this)
-->

<spring:bind path="channel.*">
<c:if test="${not empty status.errorMessages}"> 
<div class="error">
	<c:forEach items="${status.errorMessages}"
		var="errorMessage">
		<font class="error">
			<c:out value="${errorMessage}"/><br>
		</font>
	</c:forEach>
</div>
</c:if>
</spring:bind>


<form name="channel" method="post" action="<c:url value="/addChannel.html"/>" > 
	<fieldset style="width: 70%">
    <legend>Add new Channel</legend>
	<table> 
		
		<tr> 
			<th>
				URL: 
			</th> 
			<td> 
				
				<spring:bind path="channel.locationString">
	               <input size="50" id="url" type="text" name="locationString" autocomplete="off" value="URL here"/>
	           
	            </spring:bind>
	            <div id="urlpopup" class="autocomplete"/>
			</td>
			
		</tr> 
		<tr>
			<td>
				
	         </td>
		</tr>
		<tr> 
		<td><br/><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
			
	</table> 
	</fieldset>
</form> 
<ajax:autocomplete
  fieldId="url"
  popupId="urlpopup"
  targetId="url"
  paramName ="input"
  baseUrl="./autocompleteFeed.html"
  className="autocomplete"
  progressStyle="throbbing" />
<content tag="underground">

<strong><fmt:message key="common.newsagg"/></strong>
</content>
