<%@ include file="/taglibs.jsp"%> 
<title>Feed added</title> 
<p>	<h3>Feed Subscribed!</h3></p>
<!--
Roland Vecera
18.4.2005

SuccessView when FeedSubscribtion added!


-->
<p>You have added the Feed <c:out value="${feedSubscriber.channel.locationString}"/> to Category <c:out value="${feedSubscriber.category.title}"/> on <c:out value="${feedSubscriber.addedDate}"/>!</p>