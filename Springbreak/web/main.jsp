<%@ include file="/taglibs.jsp"%>
<head>
<title>Main</title>
</head>

<b><fmt:message key="main.welcome"/> <c:out value="${user.firstName}"/>!</b><br/>
LastLogin:<c:out value="${sessionScope.userSession.userData.lastLogin}" />
<br/>
<%@ include file="/itemdisplay.jsp"%>


<content tag="menubar">
<c:if test="${user.isAdmin == true}">
<a href="showUser.html"><fmt:message key="main.admin"/></a><br/>
</c:if>

<br/>
 	 	


<br/>



 



</content>




