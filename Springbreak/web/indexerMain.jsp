<head>
<%@ include file="/taglibs.jsp"%> 
<title>Indexer Home</title>    
</head>
<body>
<h3><fmt:message key="indexer.indexerHomeAdminPage"/></h3>

<table class="adminstyle"> 
	<tr>
		<td>
			Current index location:
		</td>
		<td>
			 ${indexer.indexLocation}
		</td>
	</tr>
	<tr>
		<td>
			Last index update:
		</td>
		<td>
			 ${indexer.lastIndexUpdate}
		</td>
	</tr>
	<tr>
		<td>
			Number of indexed items:
		</td>
		<td>
			 ${indexer.numberOfIndexedItems}
		</td>
	</tr>
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
