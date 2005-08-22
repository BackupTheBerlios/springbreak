<!-- 
Roland Vecera
22. August 2005

Use this .jsp to display a collection of Items


-->


<c:forEach var="i" items="${items}" varStatus="status"> 
      <div class="item<c:out value='${status.count % 2}'/>">
      <table>
      	<tr>
      		<td><c:out value="${i.title}"/> </td> <td><c:out value="${i.date}"/></td>
      	</tr>
      	<tr>
      		<td colspan="2"><c:out value="${i.description}"/></td>
      	</tr>
      </table>
      </div>
    		
</c:forEach> 