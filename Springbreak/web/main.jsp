<%@ include file="/taglibs.jsp"%>

<title>News Aggregator</title>


<h3><fmt:message key="main.welcome"/> <c:out value="${user.firstName}"/></h3>
<p>
   Hello User!
</p>


<c:forEach var="fs" items="${feedSubscribes}"> 
      <table>
      	<tr>
      		<td><c:out value="${fs.category.title}"/> </td>
      	</tr>
      <table> 
    		
</c:forEach>

<c:forEach var="i" items="${items}"> 
      <table>
      	<tr>
      		<td><c:out value="${i.title}"/> </td>
      	</tr>
      <table> 
    		
</c:forEach> 


<content tag="menubar">
<c:if test="${user.isAdmin == true}">
<a href="showUser.html"><fmt:message key="main.admin"/></a><br/>
</c:if>
<a href="addChannel.html">Add new Channel</a><br/>
<fmt:message key="main.manageRssFeeds"/><br/>

 



</content>




