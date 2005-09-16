<c:url var="logoutURL" value="logout.html">
</c:url>
<div style="width: 100%; text-align:center"><a class="menu_channel" href="${logoutURL}">logout</a></div>

<div class="manage">
 <div><fmt:message key="main.manageRssFeeds"/></div>
 	<c:url var="addCatURL" value="addCategory.html">
  </c:url>
 	<span><a class="manage" href="${addCatURL}">&gt;Add Category</a><br/></span>
	<c:url var="newChannelURL" value="addChannel.html">
  </c:url>
	<span><a class="manage" href="${newChannelURL}">&gt;Add new Channel</a><br/></span>
	<c:url var="moveFeedURL" value="moveFeed.html">
		<c:param name="action" value="input" />
  </c:url>
	<span><a class="manage" href="${moveFeedURL}">&gt;Move Channel</a></span>
 </span>
</div>