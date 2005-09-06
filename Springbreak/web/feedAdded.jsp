<head>
<title>RSS-channel added</title>
<%@ include file="/taglibs.jsp"%> 
</head>

<body>
<p>
<b>RSS-channel was parsed successfully!</b><br/>
Please select the category this RSS-Channel should be added!
</p>
<form name="channel" method="post" action="<c:url value="/subscribeChannel.html"/>" >
	<fieldset style="width: 70%">
    <legend>Subscribe RSS-channel to category!</legend>
	<table> 
		 
		<tr> 
			<th>Title: </th> 
			<td> 
				<spring:bind path="channel.title"> 
					${status.value}
				</spring:bind> 
			</td> 
		</tr> 
		<tr>
			<th>
				URL:
			</th> 
			<td> 
			    <spring:bind path="channel.locationString">
	               <input size="50" type="text" name="locationString" value="${status.value}" readonly="readonly" />
	              
	            </spring:bind>
			
				<spring:bind path="channel.id">
	               <input type="hidden" name="id" value="${status.value}"/>
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
			<th>
				Category:
			</th> 
			<td>               
          		<select name="category">  
                 <c:forEach var="cat" items="${user.categories}"> 
       				<option value="<c:out value="${cat.id}"/>"><c:out value="${cat.title}"/></option> 
                 </c:forEach> 
               </select> 
	
			</td>
			
		</tr> 
		<tr> 
		<td><br/><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
	</table>
	</fieldset>
</form> 
</body>
