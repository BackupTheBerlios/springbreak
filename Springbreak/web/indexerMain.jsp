<head>
<%@ include file="/taglibs.jsp"%> 
<title>Indexer Home</title>    
</head>
<body>
<h3><fmt:message key="indexer.indexerHomeAdminPage"/></h3>
<spring:bind path="indexer.*"> 
	<c:if test="${not empty status.errorMessages}"> 
		<div class="error"> 
			<c:forEach var="error" items="${status.errorMessages}"> 
				<c:out value="${error}" escapeXml="false"/><br /> 
			</c:forEach> 
		</div> 
	</c:if> 
</spring:bind>
<table class="adminstyle"> 
	<tr>
		<td>
				<fmt:message key="indexer.createIndexAtPosition"/>e.g e:/tmp
		</td>
		<td>
				<form method="post" action="<c:url value="/showIndexerForm.html"/>">  
						<input type="hidden" name="createIndex" value="true"/>
						<input type="text"  name="indexLocation"/>
						<input type="submit" value="<fmt:message key="indexer.createIndexAtPositionSubmitButton"/>">
				</form>
		</td>
	</tr>
</table>
</body>
