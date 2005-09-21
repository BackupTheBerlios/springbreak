<%@ include file="/taglibs.jsp"%>
<head>
<title>First Login</title>
</head>

<h3>Welcome to the Springbreak Newsagg!</h3>
This is the first time you login, so you probably need some help!<br/>
<br/>
We set up a category "My Cat" with some RSS-channels in it to simplify your start!
Just click on the left to see how the menu works!<br/>
<br/>
If you like to add your own RSS-channel follow the links
<c:url var="addChannelURL" value="addChannel.html"/>
<c:url var="addCategoryURL" value="addCategory.html"/>
<ul>
	<li><a href="${addChannelURL}">Add a RSS-channel</a></li>
	<li><a href="${addCategoryURL}">Add a category</a></li>
</ul>

<c:url var="removeFirstLoginURL" value="removeFirstLogin.html"/>
<b>
<a href="${removeFirstLoginURL}">Click here to remove this message</a> and start using the application!
</b>



<content tag="logininfo">
<div id="welcomeMessage"><fmt:message key="main.welcome"/> <b>${user.firstName}</b>! It's the first time you login!
</div>
</content>

<content tag="menubar">
<c:if test="${user.isAdmin == true}">
<a href="showUser.html"><fmt:message key="main.admin"/></a><br/>
</c:if>
<br/>
<br/>
</content>