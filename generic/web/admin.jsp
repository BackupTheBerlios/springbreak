<head>
<title>Event Google</title>
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
			<b>Index Admin:</b>
		</td>
	</tr>
	<tr>
		<td>
			<%@ include file="/upperBorder.jsp"%>
			<table border="0">
				<tr valign="top">
					<td>
						<b>Status:</b>
					</td>
					<td>
						<img src="images/eltbarborder.jpg" border="0" width="1"/><img src="images/eltbargreen.jpg" border="0" height="15px" width="<c:out value="${adminData.etlBarLength}"/>px"/><img src="images/eltbarred.jpg" border="0" height="15px" width="<c:out value="${300-adminData.etlBarLength}"/>px"/><img src="images/eltbarborder.jpg" border="0" width="1"/>
						<c:if test="${adminData.etlRunning == true}">
							<br/>Process is running since <c:out value="${adminData.etlThreadStartedAt}"/>
						</c:if>											
					</td>
				</tr>
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
						<b>Last Start Update:</b>
					</td>
					<td>
						<c:out value="${adminData.updatestart}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Last Stop Update:</b>
					</td>
					<td>
						<c:out value="${adminData.updatestop}"/>
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
						<c:if test="${adminData.etlRunning != true}">
							<form action="admin.html" method="POST">
								<input type="hidden" name="startTransformation" value="true">
								<input type="submit" value="Start Transformation"/>
							</form>
						</c:if>
					</td>
				</tr>
			</table>
			<%@ include file="/lowerBorder.jsp"%>
		</td>
		<td valign="top">
			<%@ include file="/upperBorder.jsp"%>
			<table border="0">
				<tr>
					<td>
						<u><b>Event Index:</b></u>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>

				<tr>
					<td>
						<b>Index location:</b>
					</td>
					<td>
						<c:out value="${adminData.indexLocationForEvents}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Number of indexed events:</b>
					</td>
					<td>
						<c:out value="${adminData.numberOfIndexedIemsForEvents}"/>
					</td>
				</tr>
				
				<tr>
					<td><br/></td>
					<td>&nbsp;</td>
				</tr>
				
				<tr>
					<td>
							<u><b>Correlation Index:</b></u>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
								
				<tr>
					<td>
						<b>Index location:</b>
					</td>
					<td>
						<c:out value="${adminData.indexLocationForCorrEvents}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Number of indexed events:</b>
					</td>
					<td>
						<c:out value="${adminData.numberOfIndexedIemsForCorrEvents}"/>
					</td>
				</tr>
			</table>	
			<%@ include file="/lowerBorder.jsp"%>
		</td>
	</tr>
</table>	
</body>
