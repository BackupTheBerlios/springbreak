<head>
<title>Event Google</title>
<script language="javascript" type='text/javascript' src='jscripts/showHideVisibility.js'>
</script> 
</head>
<body>
<%@ include file="/head.jsp"%>
<center><h2>> Admin <</h2></center>
<br/>
<table border="0">
	<tr valign="top">
		<td>
			<table border="0">
				<tr valign="top">
					<td bgcolor="#E5ECF9">
						<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
							<tr>
								<td bgcolor="#E5ECF9">
									<b>ETL Admin...</b>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td bgcolor="#ECECEC">
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
					</td>
				</tr>
			</table>
		</td>
		<td>
			<table border="0">
					<tr valign="top">
						<td bgcolor="#E5ECF9">
							<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
								<tr>
									<td bgcolor="#E5ECF9">
										<b>Index Admin...</b>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr valign="top">
						<td bgcolor="#ECECEC">
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
						</td>
					</tr>
				</table>
				<table border="0" width="100%" >
					<tr valign="top">
						<td bgcolor="#E5ECF9">
							<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
								<tr>
									<td bgcolor="#E5ECF9">
										<b>Search Admin...</b>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr valign="top">
						<td bgcolor="#ECECEC">
							<table border="0">
								not implemented yet
							</table>	
						</td>
					</tr>
				</table>
		</td>
		<!-- Profile Manager START -->
		<td>
			<table border="0" width="100%">
				<tr valign="top">
					<td bgcolor="#E5ECF9">
						<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
							<tr>
								<td bgcolor="#E5ECF9">
									<b>Profile Admin...</b>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr valign="top">
					<td bgcolor="#ECECEC">
						<table border="0">
							<tr>
								<td>
									<a href="admin.html?profileMenue=1">List</a> | <a href="admin.html?profileMenue=2">Add Profile</a> 
									<br/><hr size="1px"/>
										<c:choose>
											<c:when test="${adminData.profileMenue == 1}">
												<table border="0" cellspacing="0" cellpadding="4" style="border:solid #3366CC 1px">
													<tr bgcolor="#E5ECF9">
														<td>&nbsp;</td>
														<td align="center"><b>Profile</b></td>
														<td align="center"><b>Filters</b></td>
													</tr>
													<c:forEach items="${adminData.profileList}" var="profileCons" >
														<tr>
															<td valign="top" >
															<a href="admin.html?editProfile=<c:out value="${profileCons.profile.pid}"/>"><img src="images/corrPlusButton.jpg" border="0"/></a>&nbsp;<a href="admin.html?deleteProfile=<c:out value="${profileCons.profile.pid}"/>"><img src="images/corrCloseButton.jpg" border="0"/></a>
															</td>
															<td valign="top" >
																<b><c:out value="${profileCons.profile.name}"/></b>
															</td>
															<td valign="top">
																<table border="0">
																	<tr>
																		<td>
																			<b>Filter on:</b>
																		</td>
																		<td>
																			<b>Filter type:</b>
																		</td>
																	</tr>
																	<c:forEach items="${profileCons.filters}" var="filters" >
																		<tr>
																			<td>
																				<c:out value="${filters.name}"/>
																			</td>
																			<td>
																				<c:out value="${filters.ranktype}"/>
																			</td>
																		</tr>
																	</c:forEach>
																</table>
															</td>
														</tr>
													</c:forEach>
												</table>
											</c:when>
											<c:when test="${adminData.profileMenue == 2}">
											<form action="admin.html" method="get" >
												<input type="hidden" name="saveProfile" value="true">
												<c:if test="${adminData.editingProfile == true}">
													<input type="hidden" name="origprofilename" value="<c:out value="${adminData.profileCons.profile.name}"/>">
												</c:if>
												<b>Add Profile</b>
												<br/>
												<hr size="1px"/>
												<table border="0">
													<tr>
														<td>
															<table border="0">
																<tr>
																	<td><b>Name:</b></td>
																	<td><input type="text" name="profilename" size="20" value="<c:out value="${adminData.profileCons.profile.name}"/>"></td>
																	<td><input type="submit" value="Create"></td>
																</tr>
															</table>
														<td>
													</tr>
													<tr>
														<td>
															<table border="0">
																<tr>
																	<td><b>Correlations:</b></td>
																	<td><b>Eventtypes:</b></td>
																</tr>
																<tr>
																	<td>
																		<select name="profileCorrelationSet" size="10" multiple>
															      			<c:forEach items="${adminData.identifiedCorrs}" var="event">
															      				<c:if test="${adminData.editingProfile == true}">
															      					<option
																      				<c:forEach items="${adminData.profileCons.filters}" var="filter">
																	      					<c:if test="${filter.name == event}">
																								 selected
																							</c:if>
																					</c:forEach>
																					><c:out value="${event}"/></option>
																				</c:if>
																				<c:if test="${adminData.editingProfile == false}">
																					<option><c:out value="${event}"/></option>
																				</c:if>
																			</c:forEach>
																	    </select>
																	</td>
																	<td>
																		<select name="profileEventSet" size="10" multiple>
																			<c:forEach items="${adminData.identifiedEvents}" var="event">
																				<c:if test="${adminData.editingProfile == true}">
															      					<option
																      				<c:forEach items="${adminData.profileCons.filters}" var="filter">
																	      					<c:if test="${filter.name == event}">
																								 selected
																							</c:if>
																					</c:forEach>
																					><c:out value="${event}"/></option>
																				</c:if>
																				<c:if test="${adminData.editingProfile == false}">
																					<option><c:out value="${event}"/></option>
																				</c:if>
																			</c:forEach>
																	    </select>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</form>
											</c:when>
											<c:when test="${adminData.profileMenue == 666}">
												<br/><b><font color="red"><c:out value="${adminData.msg}"/></font></b>
											</c:when>
										</c:choose>
								</td>
							</tr>
						</table>	
					</td>
				</tr>
			</table>
		</td>
		<!-- Profile Manager END -->
	</tr>
</table>
</body>
