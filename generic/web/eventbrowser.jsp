<head>
<title>TEST</title>
</head>
<body>
<%@ include file="/head.jsp"%>
<h2>Browser</h2>
<br/>

<center>
<table border="0">
	<tr>
		<td>
			<b>Event List</b>
		</td>
		<td>
			<b>Event Details</b>
		</td>
	</tr>
	<tr>
		<td>
			<c:if test="${browserCmd.prevPage > 0}">
				<a href="browseEvents.html?browserPage=<c:out value="1"/>">|<</a>&nbsp;
			 	<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.prevPage}"/>"><<</a>
			</c:if>
			<c:if test="${browserCmd.currentPage < browserCmd.maxPageSize}">
				&nbsp;&nbsp;<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.nextPage}"/>">>></a>
				&nbsp;<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.maxPageSize}"/>">>|</a>
			</c:if>
			<br/>
			<table border="0">
				<tr>
					<td>Id</td>
					<td>Eventname</td>
					<td>Timestamp</td>
				</tr>
				<c:forEach items="${browserCmd.listEventsCommand}" var="eventList">
					<tr>
						
						<td>
							<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.currentPage}"/>&selectedEventId=<c:out value="${eventList.id}"/>">
								<c:out value="${eventList.id}"/>
							</a>
						</td>
						<td><c:out value="${eventList.eventtype}"/></td>
						<td><c:out value="${eventList.dbtimeCreated}"/></td>
					</tr>
				</c:forEach>
			</table>
		</td>
		<td valign="top">
			<a href="">Raw</a> | <a href="">XML</a>
			<br/>
			<c:out value="${browserCmd.eventDetail}" escapeXml="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<b>Filters:</b>
			<br/>
			<form action="">
			    <select name="top5" size="5" multiple>
			      <option>OrderReceived</option>
			      <option>DataLoggerRead</option>
			      <option>DataLoggerRead</option>
			      <option>DataLoggerRead</option>
			      <option>DataLoggerRead</option>
			      <option>DataLoggerRead</option>
			      <option>DataLoggerRead</option>
			    </select>
			    <br/>
			    <input type="submit" name="Apply" value="Apply"/>
			</form>
		</td>
		<td>
		</td>
	</tr>
</table>
</center>
</body>
