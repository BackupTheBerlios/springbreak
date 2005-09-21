 <c:if test="${sessionScope.userSession.userData.isAdmin == true}">
			<div class="manage">
 				<div>Administrator</div>
 				<table>
 				<tr><td>
 					<c:url var="showUserURL" value="showUser.html" />
						<a class="manage" href="${showUserURL}">&gt;Show Users</a>
				</td></tr>
				<tr><td>
 				<c:url var="showChannelsURL" value="showChannels.html" />
				<a class="manage" href="${showChannelsURL}">&gt;Show Channels</a>
				</td></tr>
 				</table>
			</div>
</c:if>