<%@ include file="/taglibs.jsp"%>
<head>
<title>Main</title>
</head>


<%@ include file="/itemdisplay.jsp"%>



<content tag="logininfo">
<div id="welcomeMessage"><fmt:message key="main.welcome"/> <b>${user.firstName}</b>! Your last login was on ${sessionScope.userSession.userData.lastLogin}! You have <b>${countItems}</b> new Items!
</div>
</content>






