<c:url var="logoutURL" value="logout.html">
</c:url>
<div style="width: 100%; text-align:center"><a class="menu_channel" href="${logoutURL}">logout</a></div>

<div class="manage">
 <div><fmt:message key="main.manageRssFeeds"/></div>
 	
 	<table>
 	<tr><td>
 	<c:url var="newChannelURL" value="addChannel.html" />
	<a class="manage" href="${newChannelURL}">&gt;Add new Channel</a><br/>
	</td></tr>
 	<tr><td>
 	<c:url var="moveFeedURL" value="moveFeed.html">
		<c:param name="action" value="input" />
 	</c:url>
 	<a class="manage" href="${moveFeedURL}">&gt;Move Channel</a>
 	</td></tr>
 	<tr><td>
 		<c:url var="deleteFeedURL" value="deleteFeed.html">
			<c:param name="action" value="input" />
 		</c:url>
		<a class="manage" href="${deleteFeedURL}">&gt;Remove Channel</a>
	</td></tr>
 	</table>
 	
 	<table style="margin-top: 7px">
 	
	<tr><td>
	<c:url var="addCatURL" value="addCategory.html" />
 	<a class="manage" href="${addCatURL}">&gt;Add Category</a>
 	</td></tr>
 	<tr><td>
 	<c:url var="remCatURL" value="removeCategory.html">
 		<c:param name="action" value="input" />
 	</c:url>
 	<a class="manage" href="${remCatURL}">&gt;Remove Category</a><br/>
 	</td></tr>
 	</table>
 	
</div>