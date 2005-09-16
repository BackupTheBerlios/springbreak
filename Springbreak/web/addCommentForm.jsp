<head>
<title>Add Comment</title>
<%@ include file="/taglibs.jsp"%> 
</head>

<body>

<p>
<b>Add a new comment to the RSS-channel !</b><br/>
</p>

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

<form name="comment" method="post" action="<c:url value="/addCommentStore.html"/>" > 
	<fieldset style="width: 70%">
    <legend>Add new Comment</legend>
	<table> 
		
		<tr> 
			<td>
				Title: 
			</td> 
			<td> 
				
				<spring:bind path="comment.title">
	               <input size="50" maxlength="100" type="text" name="${status.expression}" value="${status.value}"/>
	              
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
			<td>
				Text:
			</td> 
			<td> 
				
				<spring:bind path="comment.text">
	               <textarea cols="35" rows="10" name="${status.expression}" >${status.value}</textarea>
	              
	            </spring:bind>
			</td>
			
		</tr>
		<tr> 
			<td>
				Stars: 
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
	            <input name="${status.expression}" type="hidden" value="${status.value}"/>
	           </spring:bind>
			</td>
			
		</tr>
		
		<tr> 
		<td><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
			
	</table> 
	</fieldset>
</form>
</body> 
<content tag="underground">

<strong><fmt:message key="common.newsagg"/></strong>
</content>
