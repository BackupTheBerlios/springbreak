<head>
<title>Event Google</title>
</head>
<body>
<%@ include file="/head.jsp"%>
<center><h2>> Search Results <</h2></center>
<br/>
<table border="0">
	<tr>
		<td valign="top">
			<table border="0">
				<tr>
					<td bgcolor="#E5ECF9">
						<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
							<tr>
								<td>
									<b><c:out value="${searchResult.numberOfResults}"/> Results from Event Space</b>
								</td>
								<td align="right">
									Results <b>1 - *</b> (<b><c:out value="${searchResult.queryTime}"/></b> mseconds) 
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<c:forEach items="${searchResult.foundCorrSet}" var="foundCorrSet" >
				<tr>
					<td bgcolor="#ECECEC">
						<table border="0">
							<tr>
								<td  valign="top">
									<img src="images/corrPlusButton.jpg"/>
								</td>
								<td  valign="top">
									Correlated Events by <c:out value="${foundCorrSet.correlationSetDef}"/><br/>
									<hr/>
									<c:forEach items="${foundCorrSet.eventAgg}" var="eventAgg">
										<c:out value="${eventAgg.eventTypeName}"/>
									</c:forEach>		
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</c:forEach>
			</table>	
		</td>
		<td valign="top">
			
		</td>
	</tr>
</body>
	