<%@ include file="/taglibs.jsp"%> 
<title>Feed added</title> 
<p>	<h3>Subscribe Feed?</h3></p> 

<table> 

<form name="channel" method="post" action="<c:url value="/subscribeChannel.html"/>" >
	<table> 
		 
		<tr> 
			<th>TITLE </th> 
			<td> 
				<spring:bind path="channel.title"> 
					${status.value}
				</spring:bind> 
			</td> 
		</tr> 
		<tr>
			<th>
				URL
			</th> 
			<td> 
			    <spring:bind path="channel.locationString">
	               <input type="text" name="locationString" value="${status.value}" readonly="readonly" />
	              
	            </spring:bind>
			
				<spring:bind path="channel.id">
	               <input type="hidden" name="id" value="${status.value}"/>
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
			<th>
				ID
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
		<td><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
	</table>
</form> 
</table>