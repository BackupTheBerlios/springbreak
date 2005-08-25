<%@ include file="/taglibs.jsp"%>

<title>News Aggregator</title>


<h3><fmt:message key="main.welcome"/> <c:out value="${user.firstName}"/>!</h3>


<%@ include file="/itemdisplay.jsp"%>


<content tag="menubar">
<c:if test="${user.isAdmin == true}">
<a href="showUser.html"><fmt:message key="main.admin"/></a><br/>
</c:if>
<a href="addChannel.html">Add new Channel</a><br/>
<br/>
<c:set var="lastcategory" value=""/>

<c:forEach var="fs" items="${feedSubscribes}"> 
      
      <c:if test="${fs.category.title != lastcategory}"> 
      		<div class="category">
      		
      		   <a href="main.html?category_id=${fs.category.id}">${fs.category.title}</a>
      		</div>
      </c:if>
      
      <div class="channel">
      		
      		<a href="main.html?channel_id=${fs.channel.id}">${fs.channel.title}</a>
      		<a href="showCommentsToChannel.html?id=<c:out value='${fs.channel.id}'/>">#</a>
      		<a href="addComment.html?id=<c:out value='${fs.channel.id}'/>">*</a>
            <a href="showSubscribersStatistic.html?channel_id=<c:out value='${fs.channel.id}'/>&numberWeeks=30">~</a> </td>     
      </div>
    <c:set var="lastcategory" value="${fs.category.title}"/>  
</c:forEach>   		


<br/>
<div class="manage">
<fmt:message key="main.manageRssFeeds"/><br/>
<a href="addCategory.html">Add Category</a>
</div>


 



</content>




