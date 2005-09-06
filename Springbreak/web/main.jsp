<%@ include file="/taglibs.jsp"%>
<head>
<title>Main</title>
</head>

<h3><fmt:message key="main.welcome"/> <c:out value="${user.firstName}"/>!</h3>
LastLogin:<c:out value="${sessionScope.userSession.userData.lastLogin}" />
<%@ include file="/itemdisplay.jsp"%>


<content tag="menubar">
<c:if test="${user.isAdmin == true}">
<a href="showUser.html"><fmt:message key="main.admin"/></a><br/>
</c:if>

<br/>
 	 	


<br/>



 



</content>




