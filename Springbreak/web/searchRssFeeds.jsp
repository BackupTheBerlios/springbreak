<head>
<%@ include file="/taglibs.jsp"%> 
<title>Search</title>    
</head>
<body>
<h3><fmt:message key="indexerSearch.searchtitle"/></h3>

<form method="post" action="<c:url value="/showSearch.html"/>">  
	<center>
	<input type="text"  name="search"/>&nbsp;<input type="submit" value="<fmt:message key="indexerSearch.searchbuttonlabel"/>">
	</center>
	<br/>
	<br/>
	<c:forEach var="rssItem" items="${rssItems}" varStatus="status"> 
		<table class="adminstyle"> 
			<tr>
				<td>${rssItem.title}</td>
			</tr>
			<tr>
				<td>${rssItem.description}</td>
			</tr>
		</table>
		<br/>
		<br/>
	</c:forEach> 
</form>
</body>
