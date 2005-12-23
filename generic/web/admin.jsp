<head>
<title>TEST</title>
</head>
<body>
<%@ include file="/head.jsp"%>
<center><h2>> Admin <</h2></center>
<br/>
<table border="0">
	<tr>
		<td>
			<b>ETL Admin:</b>
		</td>
		<td>
			<b>Lucene Admin:</b>
		</td>
	</tr>
	<tr>
		<td>
			<%@ include file="/upperBorder.jsp"%>
			<table border="0">
				<tr>
					<td>
						<b>Identified Items:</b>
					</td>
					<td>
						<c:out value="${adminData.numberOfIdentifiedEvents}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Processed Items:</b>
					</td>
					<td>
						<c:out value="${adminData.numberOfProcessedEvents}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Last Update:</b>
					</td>
					<td>
						<c:out value="${adminData.lastUpdate}"/>
					</td>
				</tr>
				<tr valign="top">
					<td>
						<b>Identified Eventtypes:</b>
					</td>
					<td>
						<c:forEach items="${adminData.identifiedEvents}" var="event">
							<c:out value="${event}"/><br/>
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
			<%@ include file="/lowerBorder.jsp"%>
		</td>
		<td>
			<%@ include file="/upperBorder.jsp"%>
			<table border="0">
				<tr>
					<td>
							<br/>
							<br/>
							<br/><br/><br/><br/>
					</td>
				</tr>
			</table>	
			<%@ include file="/lowerBorder.jsp"%>
		</td>
	</tr>
</table>	
</body>
