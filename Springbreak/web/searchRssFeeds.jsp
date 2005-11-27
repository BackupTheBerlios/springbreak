<head>
<%@ include file="/taglibs.jsp"%> 
<title>Search</title>    
</head>
<body>
<h3><fmt:message key="indexerSearch.searchtitle"/></h3>

<form method="post" action="<c:url value="/showSearch.html"/>">  
	<table class="adminstyle"> 
		<tr>
			<td>
					 <input type="text"  name="search"/>
			</td>
			<td>
					<input type="submit" value="<fmt:message key="indexerSearch.searchbuttonlabel"/>">
			</td>
		</tr>
	</table>
	<c:forEach var="rssItem" items="${rssItems}" varStatus="status"> 
		${rssItem.id}<br/>
	</c:forEach> 
</form>
</body>
