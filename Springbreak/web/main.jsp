<%@ include file="/taglibs.jsp"%>

<title>News Aggregator</title>


<h3><fmt:message key="main.welcome"/> <c:out value="${user.firstName}"/>!</h3>
LastLogin:<c:out value="${sessionScope.userSession.userData.lastLogin}" />
<%@ include file="/itemdisplay.jsp"%>


<content tag="menubar">
<c:if test="${user.isAdmin == true}">
<a href="showUser.html"><fmt:message key="main.admin"/></a><br/>
</c:if>

<br/>
 	 	


<br/>
<div class="manage">
<fmt:message key="main.manageRssFeeds"/><br/>
<a href="addCategory.html">Add Category</a><br/>
<a href="addChannel.html">Add new Channel</a><br/>
<a href="moveFeed.html?action=input">Move Channel</a>
</div>


 



</content>




