<%@ include file="/taglibs.jsp"%> 
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<script type="text/javascript" src="./ajaxcore.js"></script>

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
	               <input id="url" type="text" name="locationString" autocomplete="off" value="URL here"/>
	               
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
		<td><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
			
	</table> 
</form> 
<ajax:autocomplete
  fieldId="url"
  popupId="url-popup"
  targetId="url"
  paramName ="input"
  baseUrl="./autocompleteFeed.html"
  className="autocomplete"
  progressStyle="throbbing" />
<content tag="underground">

<strong><fmt:message key="common.newsagg"/></strong>
</content>
