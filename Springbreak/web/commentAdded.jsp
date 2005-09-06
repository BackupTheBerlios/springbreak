<head>
<%@ include file="/taglibs.jsp"%> 
<title>Comment added</title> 
</head>

<body>
<h3>Comment added</h3>
<p>You have added the Comment <c:out value="${comment.title}"/> to Channel <c:out value="${comment.channel.id}"/>!</p>
</body>