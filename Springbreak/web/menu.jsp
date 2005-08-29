<c:set var="lastcategory" value=""/>


<c:forEach var="fs" items="${sessionScope.feedSubscriberSession.feedSubscribers}"> 
      
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