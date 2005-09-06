<head>
<%@ include file="/taglibs.jsp"%> 
<title>Feed added</title>
</head>
<body> 
<p>	<h3>Feed Subscribed!</h3></p>

<p>You have added the Feed <c:out value="${feedSubscriber.channel.locationString}"/> to Category <c:out value="${feedSubscriber.category.title}"/> on <c:out value="${feedSubscriber.addedDate}"/>!</p>
</body>