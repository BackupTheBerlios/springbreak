<head>
<title>Event Google</title>
</head>
<body>
<%@ include file="/head.jsp"%>
<center><h2>> Browser <</h2></center>
<br/>
<table border="0">
	<tr>
		<td>
			<b>Event List:</b>
		</td>
		<td>
			<b>Event Details:</b>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table border="0">
				<tr>
					<td>
						<%@ include file="/upperBorder.jsp"%>
						<center>
						<c:if test="${browserCmd.prevPage > 0}">
							<a href="browseEvents.html?browserPage=<c:out value="1"/>"><img src="images/arrowLeftStop.gif" border="0"></a>&nbsp;
						 	<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.prevPage}"/>"><img src="images/arrowLeft.gif" border="0"></a>
						</c:if>
						<c:if test="${browserCmd.currentPage < browserCmd.maxPageSize}">
							&nbsp;&nbsp;<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.nextPage}"/>"><img src="images/arrowRight.gif" border="0"></a>
							&nbsp;<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.maxPageSize}"/>"><img src="images/arrowRightStop.gif" border="0"></a>
						</c:if>
						</center>
						<br/>
						<table border="0">
							<tr>
								<td><b>Id</b></td>
								<td><b>Eventname</b></td>
								<td><b>Timestamp</b></td>
							</tr>
							<c:forEach items="${browserCmd.listEventsCommand}" var="eventList">
								<tr>
									
									<td>
										<a href="browseEvents.html?browserPage=<c:out value="${browserCmd.currentPage}"/>&selectedEventId=<c:out value="${eventList.eventid}"/>">
											<c:out value="${eventList.eventid}"/>
										</a>
									</td>
									<td><c:out value="${eventList.eventtype}"/></td>
									<td><c:out value="${eventList.localtimeid}"/></td>
								</tr>
							</c:forEach>
						</table>
						<%@ include file="/lowerBorder.jsp"%>
					</td>
				</tr>
				<tr>
					<td>
						<b>Filters:</b>
						<%@ include file="/upperBorder.jsp"%>
						<table border="0">
						<tr>
						<td>
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
				    	</tr>
				    	</table>
						<%@ include file="/lowerBorder.jsp"%>
					</td>
				</tr>
			</table>
			
		</td>
		<td>
			<%@ include file="/upperBorder.jsp"%>
			<table border="0">
			<tr>
			<td>
				<a href="">Raw</a> | <a href="">XML</a>
				<br/>
	    		<textarea name="user_eingabe" readonly  cols="70" rows="30" ><c:out value="${browserCmd.eventDetail}" escapeXml="true"/></textarea>
	    	</td>
	    	</tr>
	    	</table>
			<%@ include file="/lowerBorder.jsp"%>
		</td>
	</tr>
</table>
</body>
