<head>
<title>Event Google</title>

<script language="javascript" type='text/javascript' src='jscripts/wordHighlighter.js'>

</script> 
</head>
<%@ include file="/taglibs.jsp"%>
<!-- body onload="highlightSearchTerms('<c:out value="${searchResult.termList}"/>');" -->
<%@ include file="/head.jsp"%>
<center><h2>> Search Results <</h2></center>
<br/>
<!-- Navigation Bar -->
<center>
<c:if test="${searchResult.numberOfFoundCorrEvents >= searchResult.maxSearchResults}">
	<c:if test="${searchResult.prevPage > 0}">
		<a href="search.html?browserPage=1&ranksearchtype=rankonesearch"><img src="images/arrowLeftStop.gif" border="0"></a>&nbsp;
	 	<a href="search.html?browserPage=<c:out value="${searchResult.prevPage}"/>&ranksearchtype=rankonesearch"><img src="images/arrowLeft.gif" border="0"></a>
	</c:if>
	<c:forEach begin="1" end="${searchResult.maxPageSize}" var="runner">
		<c:if test="${runner != searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&ranksearchtype=rankonesearch"><c:out value="${runner}"/></a>&nbsp;
		</c:if>
		<c:if test="${runner == searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&ranksearchtype=rankonesearch"><font color="red"><b><c:out value="${runner}"/></b></font></a>&nbsp;
		</c:if>
	</c:forEach> 
	<c:if test="${searchResult.currentPage < searchResult.maxPageSize}">
		&nbsp;&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.nextPage}"/>&ranksearchtype=rankonesearch"><img src="images/arrowRight.gif" border="0"></a>
		&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.maxPageSize}"/>&ranksearchtype=rankonesearch"><img src="images/arrowRightStop.gif" border="0"></a>
	</c:if>
</c:if>
</center>
<!-- Navigation Bar -->
<table border="0" width="100%">
	<tr>
		<!-- Filter Bar--> 
		<td valign="top" width="*">
			<c:if test="${searchResult.showRefineQuery == true}">
				<table border="0" width="250px">
					<tr>
						<td bgcolor="#E5ECF9">
							<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
								<tr>
									<td bgcolor="#E5ECF9">
										<table border="0" cellspacing="0" cellpadding="0" width="100%">
											<tr>
											<td><b>Refine Query...</b></td>
												<td align="right"><a href="search.html?showRefineQuery=false&ranksearchtype=rankonesearch"><img src="images/corrCloseButton.jpg" border="0"/></a></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td bgcolor="#ECECEC">
							<c:forEach items="${searchResult.foundEventtypes}" var="eventTypes" >
								<c:if test="${eventTypes.value == false}">
									<a href="search.html?browserPage=1&changeEventFilter=<c:out value="${eventTypes.key}"/>&changeEventFilterTo=true&ranksearchtype=rankonesearch"><img src="images/corrMinusButton.jpg" border="0"/><c:out value="${eventTypes.key}"/></a>
								</c:if>
								<c:if test="${eventTypes.value == true}">
									<a href="search.html?browserPage=1&changeEventFilter=<c:out value="${eventTypes.key}"/>&changeEventFilterTo=false&ranksearchtype=rankonesearch"><img src="images/corrPlusButton.jpg" border="0"/><c:out value="${eventTypes.key}"/></a>
								</c:if>
								<br/>
							</c:forEach>
						</td>
					</tr>
				</table>
				<table border="0" width="250px">
					<tr>
						<td bgcolor="#E5ECF9">
							<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
								<tr>
									<td bgcolor="#E5ECF9">
										<b>Date Range...</b></td>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td bgcolor="#ECECEC">	
							<form action="search.html" method="get" >
								<%@ include file="/dateselector.jsp"%>
								<br/>
								<input type="hidden" name="browserPage" value="1">
								<input type="hidden" name="dateRangeChanged" value="true">
								<input type="hidden" name="ranksearchtype" value="rankonesearch">
								<input type="submit" value="Go">
							</form>
						</td>
					</tr>
				</table>
			</c:if>
		</td>
		<!-- Filter Bar--> 
		<td valign="top" width="100%">
			<table border="0" width="100%">
				<tr>
					<c:if test="${searchResult.showRefineQuery == false}">
						<a href="search.html?showRefineQuery=true&ranksearchtype=rankonesearch"><font size="8px">more options...</font></a>
					</c:if>
					<td bgcolor="#E5ECF9">
						<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
							<tr>
								<td>
									<b><c:out value="${searchResult.numberOfFoundCorrEvents}"/> Results from Event Space</b>
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
				<c:forEach items="${searchResult.foundCorrSet}" var="eventAgg" >
				<tr>
					<td bgcolor="#ECECEC">
						<table border="0">
							<tr>
								<td  valign="top">
									&nbsp;
								</td>
								<td  valign="top">
										<c:if test="${searchResult.showEventId eq eventAgg.event.eventid}">
											<c:set var="xmlcontent" value="${eventAgg.event.xmlcontent}"/>
											<c:set var="attributeListForOutput" value="${eventAgg.eventAttributes}"/>
										</c:if> 
										<a href="search.html?showEventId=<c:out value="${eventAgg.event.eventid}"/>&showEventViewType=styled&ranksearchtype=rankonesearch">
										<b><c:out value="${eventAgg.eventTypeName}"/></b></a>
										(
										<c:forEach items="${eventAgg.eventAttributes}" var="attrib" varStatus="attribStatus">
											<c:out value="${attrib.attributename}"/>:<c:out value="${attrib.value}"/><c:if test="${attribStatus.last != true}">,</c:if>
										</c:forEach>	
										)
										<br/>
										<a href="search.html?showContextForId=<c:out value="${eventAgg.event.eventid}"/>&showContextForEvent=<c:out value="${eventAgg.eventTypeName}"/>"><img src="images/arrowRight.gif" border="0">show correlation context...</a>
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
				<c:if test="${searchResult.showRefineQuery == false}">
					<br/>
				</c:if>
				<table border="0" width="100%">
					<tr>
						<td bgcolor="#E5ECF9">
							<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
								<tr>
									<td bgcolor="#E5ECF9">
										<table width="100%" cellspacing="0" cellpadding="0">
											<tr>
												<td align="left">
													<a href="search.html?showEventId=<c:out value="${searchResult.showEventId}"/>&showEventViewType=raw&ranksearchtype=rankonesearch">Raw</a> | <a href="search.html?showEventId=<c:out value="${searchResult.showEventId}"/>&showEventViewType=styled&ranksearchtype=rankonesearch">Styled</a>
												</td>
												<td align="right">
													<a href="search.html?closeShowEventId=true&ranksearchtype=rankonesearch"><img src="images/corrCloseButton.jpg" border="0"/></a>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td bgcolor="#ECECEC">	
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
			</c:if>
		</td>
	</tr>
</table>
<!-- Navigation Bar -->
<center>
<c:if test="${searchResult.numberOfFoundCorrEvents >= searchResult.maxSearchResults}">

	<c:if test="${searchResult.prevPage > 0}">
		<a href="search.html?browserPage=1&ranksearchtype=rankonesearch"><img src="images/arrowLeftStop.gif" border="0"></a>&nbsp;
	 	<a href="search.html?browserPage=<c:out value="${searchResult.prevPage}"/>&ranksearchtype=rankonesearch"><img src="images/arrowLeft.gif" border="0"></a>
	</c:if>
	<c:forEach begin="1" end="${searchResult.maxPageSize}" var="runner">
		<c:if test="${runner != searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&ranksearchtype=rankonesearch"><c:out value="${runner}"/></a>&nbsp;
		</c:if>
		<c:if test="${runner == searchResult.currentPage}">
			<a href="search.html?browserPage=<c:out value="${runner}"/>&ranksearchtype=rankonesearch"><font color="red"><b><c:out value="${runner}"/></b></font></a>&nbsp;
		</c:if>
	</c:forEach> 
	<c:if test="${searchResult.currentPage < searchResult.maxPageSize}">
		&nbsp;&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.nextPage}"/>&ranksearchtype=rankonesearch"><img src="images/arrowRight.gif" border="0"></a>
		&nbsp;<a href="search.html?browserPage=<c:out value="${searchResult.maxPageSize}"/>&ranksearchtype=rankonesearch"><img src="images/arrowRightStop.gif" border="0"></a>
	</c:if>
</c:if>
</center>
<!-- Navigation Bar -->
</body>