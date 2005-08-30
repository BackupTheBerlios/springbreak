




<%@ include file="/taglibs.jsp"%> 
<title>Springbreak Add Comment</title> 
<p>Fill in Comment </p> 


<!--
Roland Vecera
Input für AddCommentController


TODO: was macht onsubmit="return validateChannel(this)
-->


<spring:bind path="comment.*">
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


<form name="comment" method="post" action="<c:url value="/addComment.html"/>" > 
	<table> 
		
		<tr> 
			<td>
				Title 
			</td> 
			<td> 
				
				<spring:bind path="comment.title">
	               <input maxlength="100" type="text" name="${status.expression}" value="${status.value}"/>
	              
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
			<td>
				Text
			</td> 
			<td> 
				
				<spring:bind path="comment.text">
	               <textarea name="${status.expression}" >
	              ${status.value}
	               </textarea>
	              
	            </spring:bind>
			</td>
			
		</tr>
		<tr> 
			<td>
				stars 
			</td> 
			<td> 
				
				<spring:bind path="comment.stars">
	               <select name="stars">
	                <option value="0">0 (...very bad)</option>
	                <option value="1">1</option>
	                <option value="2">2</option>
	                <option selected value="3">3</option>
	                <option value="4">4</option>
	                <option value="5">5 (...great)</option>
	               </select>
	              
	            </spring:bind>
	            
	           <spring:bind path="comment.channel_id">
	            <input name="channel_id" type="hidden" value="<c:out value="${param.id}"/>"/>
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
