<head>
<title>TEST</title>
</head>
<body>
<%@ include file="/head.jsp"%>
<h2>Admin</h2>
<br/>

<table border="0">
	<tr>
		<td>
			Identified Items
		</td>
		<td>
			<c:out value="${adminData.numberOfIdentifiedEvents}"/>
		</td>
	</tr>
	<tr>
		<td>
			Processed Items
		</td>
		<td>
			<c:out value="${adminData.numberOfProcessedEvents}"/>
		</td>
	</tr>
	<tr>
		<td>
			Last Update
		</td>
		<td>
			<c:out value="${adminData.lastUpdate}"/>
		</td>
	</tr>
	<tr>
		<td>
			Identified Eventtypes
		</td>
		<td>
			<c:forEach items="${adminData.identifiedEvents}" var="event">
				<c:out value="${event}"/>
			</c:forEach>
		</td>
	</tr>
	<tr>		
		<td>
			&nbsp;
		</td>
		<td>
			<form action="admin.html" method="POST">
				<input type="hidden" name="startTransformation" value="true">
				<input type="submit" value="Start Transformation"/>
			</form>
		</td>
	</tr>
</table>
</body>
