<head>
<title>TEST</title>
</head>
<body>
<%@ include file="/head.jsp"%>
Browser
<br/>


<c:forEach items="${eventCmd}" var="eventList">
	<c:out value="${eventList.eventId}"/> - <c:out value="${eventList.eventType}"/> - <c:out value="${eventList.dateTime}"/> <br/>
</c:forEach>

</body>
