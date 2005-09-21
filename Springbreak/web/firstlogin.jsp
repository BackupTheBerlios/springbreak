<%@ include file="/taglibs.jsp"%>
<head>
<title>First Login</title>
<script type="text/javascript" src="./fat.js"></script>
</head>

<body>
<div style="padding: 5px; margin: 5px;" id="firstLoginDiv" class="fade">
<h3>Welcome to the Springbreak Newsagg!</h3>
This is the first time you login, so you probably need some help!<br/>
<br/>
We set up a category "My Category" with some RSS-channels in it to simplify your start!
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
</div>
</body>

<content tag="logininfo">
<div id="welcomeMessage"><fmt:message key="main.welcome"/> <b>${user.firstName}</b>! It's the first time you login!
</div>
</content>

