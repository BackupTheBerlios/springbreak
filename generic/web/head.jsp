<head>
<%@ include file="/taglibs.jsp"%>
<title>Events</title>
<link REL="stylesheet" HREF="styles/style.css" TYPE="text/css">
</head>
<body>
<center>
	<!--  <h1>Event Google</h1>  -->
	<a href="index.html"><img src="images/eventlogo.jpg" border="0" /></a>
	
<table border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
	  <td><img src="images/borderUpper600.jpg" width="600" height="10"></td>
	</tr>
  	<tr>
   		<td background="images/borderMiddle600.jpg" align="center">
			<table border="0">
				<tr>
					<td>
						<a href="browseEvents.html">Browse Events</a> | <a href="admin.html">Admin</a> | ... | ...
					</td>
				</tr>
				<tr>
					<td>
						<form action="search.html" method="post">
							<center><input type="text" name="searchstring" size="55" value="<c:out value="${searchResult.searchString}"/>"/><br/>
							<input type="hidden" name="newSearch" value="true"/>
							<input type="submit" value="Event-Search"/> </center>
						</form>
					</td>
				</tr>		
			</table>
		</td>
	</tr>
	<tr>
	  <td><img src="images/borderBottom600.jpg" width="600"></td>
	</tr>
</table>
</center>
</body>


