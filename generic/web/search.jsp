<head>
<title>Event Google</title>

<script language="javascript" type='text/javascript' src='jscripts/wordHighlighter.js'>

</script> 
</head>
<%@ include file="/taglibs.jsp"%>
<c:if test="${not empty searchResult.termList}">
<body onload="highlightSearchTerms('<c:out value="${searchResult.termList}"/>');">
</c:if>
<c:if test="${empty searchResult.termList}">
<body>
</c:if>
<%@ include file="/head.jsp"%>
<center><h2>> Search Results <</h2></center>
<br/>
<!-- Navigation Bar -->
<center>
<c:if test="${searchResult.numberOfFoundCorrEvents >= searchResult.maxSearchResults}">
	<c:if test="${searchResult.prevPage > 0}">
		<a href="search.html?browserPage=1&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowLeftStop.gif" border="0"></a>&nbsp;
	 	<a href="search.html?browserPage=<c:out value="${searchResult.prevPage}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowLeft.gif" border="0"></a>
	</c:if>
	<c:forEach begin="1" end="${searchResult.maxPageSize}" var="runner">
		<c:if test="${runner != searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><c:out value="${runner}"/></a>&nbsp;
		</c:if>
		<c:if test="${runner == searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><font color="red"><b><c:out value="${runner}"/></b></font></a>&nbsp;
		</c:if>
	</c:forEach> 
	<c:if test="${searchResult.currentPage < searchResult.maxPageSize}">
		&nbsp;&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.nextPage}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowRight.gif" border="0"></a>
		&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.maxPageSize}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowRightStop.gif" border="0"></a>
	</c:if>
</c:if>
</center>
<!-- Navigation Bar -->
<table border="0" width="100%">
	<tr>
		<td valign="top">
			<table border="0" width="100%">
				<tr>
					<td bgcolor="#E5ECF9">
						<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
							<tr>
								<td>
									<c:choose>
									<c:when test="${empty param.showContextForEvent}">
										<b><c:out value="${searchResult.numberOfFoundCorrEvents}"/> Results from Event Space</b>
									</c:when>
									<c:otherwise>
										<b><c:out value="${searchResult.numberOfFoundCorrEvents}"/> Context Results from Event Space for the Event <c:out value="${param.showContextForEvent}"/> </b>
									</c:otherwise>
									</c:choose>
								</td>
								<td align="right">
									<c:if test="${searchResult.currentPage >= searchResult.maxPageSize}">
										Results <b><c:out value="${searchResult.currentPage * searchResult.maxSearchResults - searchResult.maxSearchResults + 1}"/> - <c:out value="${searchResult.numberOfFoundCorrEvents}"/></b> (<b><c:out value="${searchResult.queryTime/1000}"/></b> seconds) 
									</c:if>
									<c:if test="${searchResult.currentPage < searchResult.maxPageSize}">	
										Results <b><c:out value="${searchResult.currentPage * searchResult.maxSearchResults - searchResult.maxSearchResults + 1}"/> - <c:out value="${searchResult.currentPage * searchResult.maxSearchResults}"/></b> (<b><c:out value="${searchResult.queryTime/1000}"/></b> seconds) 
									</c:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<c:forEach items="${searchResult.foundCorrSet}" var="foundCorrSet" varStatus="foundCorrSetStatus" >
				<tr>
					<td bgcolor="#ECECEC">
						<table border="0">
							<tr>
								<td  valign="top">
									<c:choose>
										<c:when test="${searchResult.showCorrEventId[foundCorrSet.guid] eq foundCorrSet.guid}">
											<a href="search.html?showCorrEventId=<c:out value="${foundCorrSet.guid}"/>"><img src="images/corrMinusButton.jpg" border="0"/></a>
										</c:when>
										<c:otherwise>
											<a href="search.html?showCorrEventId=<c:out value="${foundCorrSet.guid}"/>"><img src="images/corrPlusButton.jpg" border="0"/></a>											
										</c:otherwise>
									</c:choose>
								</td>
									<td  valign="top">
										Correlated Events by <c:out value="${foundCorrSet.correlationSetDef}"/><br/>
										<hr/>
										<c:out value="${eventAgg.eventTypeName}"/>
										<c:forEach items="${foundCorrSet.eventAgg}" var="eventAgg" varStatus="eventAggStatus">  
											<c:choose>
												<c:when test="${searchResult.showCorrEventId[foundCorrSet.guid] eq foundCorrSet.guid}">
													<c:if test="${searchResult.showEventId eq eventAgg.event.eventid}">
														<c:set var="xmlcontent" value="${eventAgg.event.xmlcontent}"/>
														<c:set var="attributeListForOutput" value="${eventAgg.eventAttributes}"/>
													</c:if> 
													<a href="search.html?showEventId=<c:out value="${eventAgg.event.eventid}"/>&showEventViewType=styled">
													<b><c:out value="${eventAgg.eventTypeName}"/></b></a>
													(
													<c:forEach items="${eventAgg.eventAttributes}" var="attrib" varStatus="attribStatus">
														<c:out value="${attrib.attributename}"/>:<c:out value="${attrib.value}"/><c:if test="${attribStatus.last != true}">,</c:if>
													</c:forEach>	
													)
												</c:when>
												<c:otherwise>
													<c:forEach items="${foundCorrSet.eventRank}" var="eventRank">
														<c:if test="${eventRank eq eventAgg.event.eventid}">
															<c:if test="${searchResult.showEventId eq eventAgg.event.eventid}">
																<c:set var="xmlcontent" value="${eventAgg.event.xmlcontent}"/>
																<c:set var="attributeListForOutput" value="${eventAgg.eventAttributes}"/>
															</c:if> 
															<a href="search.html?showEventId=<c:out value="${eventAgg.event.eventid}"/>&showEventViewType=styled">
															<b><c:out value="${eventAgg.eventTypeName}"/></b></a>
															(
															<c:forEach items="${eventAgg.eventAttributes}" var="attrib" varStatus="attribStatus">
																<c:out value="${attrib.attributename}"/>:<c:out value="${attrib.value}"/><c:if test="${attribStatus.last != true}">,</c:if>
															</c:forEach>	
															)
														</c:if>
													</c:forEach>
												</c:otherwise>
											</c:choose>
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
			<c:if test="${not empty searchResult.showEventId}">
				<%@ include file="/upperBorder.jsp"%>
				<table border="0" cellspacing="0" cellpadding="3" width="100%" >
					<tr>
						<td>
							<table width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td align="left">
									<a href="search.html?showEventId=<c:out value="${searchResult.showEventId}"/>&showEventViewType=raw">Raw</a> | <a href="search.html?showEventId=<c:out value="${searchResult.showEventId}"/>&showEventViewType=styled">Styled</a>
								</td>
								<td align="right">
									<a href="search.html?closeShowEventId=true"><img src="images/corrCloseButton.jpg" border="0"/></a>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td>
							<c:if test="${searchResult.showEventViewType eq 'raw'}">
								<textarea name="user_eingabe" readonly  cols="70" rows="30" ><c:out value="${xmlcontent}" escapeXml="true"/></textarea>
							</c:if>
							<c:if test="${searchResult.showEventViewType eq 'styled'}">
								<table border="0" cellspacing="0" cellpadding="2">
									<c:forEach items="${attributeListForOutput}" var="attribs" varStatus="status">
											<tr>
												<td><b><c:out value="${attribs.attributename}"/>:</b></td>
												<td><c:out value="${attribs.value}"/></td>
											</tr>
									</c:forEach>
								</table>
							</c:if>
				    	</td>
			    	</tr>
		    	</table>
				<%@ include file="/lowerBorder.jsp"%>
			</c:if>
		</td>
	</tr>
</table>
<!-- Navigation Bar -->
<center>
<c:if test="${searchResult.numberOfFoundCorrEvents >= searchResult.maxSearchResults}">
	<c:if test="${searchResult.prevPage > 0}">
		<a href="search.html?browserPage=1&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowLeftStop.gif" border="0"></a>&nbsp;
	 	<a href="search.html?browserPage=<c:out value="${searchResult.prevPage}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowLeft.gif" border="0"></a>
	</c:if>
	<c:forEach begin="1" end="${searchResult.maxPageSize}" var="runner">
		<c:if test="${runner != searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><c:out value="${runner}"/></a>&nbsp;
		</c:if>
		<c:if test="${runner == searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><font color="red"><b><c:out value="${runner}"/></b></font></a>&nbsp;
		</c:if>
	</c:forEach> 
	<c:if test="${searchResult.currentPage < searchResult.maxPageSize}">
		&nbsp;&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.nextPage}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowRight.gif" border="0"></a>
		&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.maxPageSize}"/>&exactSearch=<c:out value="${searchResult.exactSearch}"/>"><img src="images/arrowRightStop.gif" border="0"></a>
	</c:if>
</c:if>
</center>
<!-- Navigation Bar -->
</body>
	