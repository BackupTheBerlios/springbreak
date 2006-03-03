<head>
<title>Event Google</title>
<script language=javascript type='text/javascript'>
function hidediv(pass) {
var divs = document.getElementsByTagName('div');
for(i=0;i<divs.length;i++){
if(divs[i].id.match(pass)){//if they are 'see' divs
if (document.getElementById) // DOM3 = IE5, NS6
divs[i].style.display="none";// show/hide
else
if (document.layers) // Netscape 4
document.layers[divs[i]].display = 'hidden';
else // IE 4
document.all.hideShow.divs[i].visibility = 'hidden';
}
}
}

function showdiv(pass) {
var divs = document.getElementsByTagName('div');
for(i=0;i<divs.length;i++){
if(divs[i].id.match(pass)){
if (document.getElementById)
divs[i].style.display="block";
else
if (document.layers) // Netscape 4
document.layers[divs[i]].display = 'visible';
else // IE 4
document.all.hideShow.divs[i].visibility = 'visible';
}
}
}
</script> 
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
</body>
	