<script type="text/javascript">
function manage(val) {
 
 if (document.getElementById("menu_category"+val).style.display =='inline')
  {
  document.getElementById("menu_category"+val).style.display ='none';
  document.getElementById("menu_category"+val).style.visibility ='hidden';
  }
 else
  { 
  document.getElementById("menu_category"+val).style.display ='inline';
  document.getElementById("menu_category"+val).style.visibility ='visible';
  }
 
}
</script>



<c:set var="lastcategory" value=""/>


<c:forEach var="fs" items="${sessionScope.feedSubscriberSession.feedSubscribers}"> 
      
      <c:if test="${fs.category.title != lastcategory}"> 
      	<c:if test="${lastcategory != ''}">
      			</div>
      		</div>
      	</c:if>
      		
      		<div style="margin-top: 5px; padding: 3px; background:${fs.category.htmlColor}" >
      			<div style=""><input type="button" value="X" onclick="manage(${fs.category.id})"></input></div>
      		   <a href="main.html?category_id=${fs.category.id}">${fs.category.title}</a>
      			<div style="display:none; visibility: hidden;" id="menu_category${fs.category.id}">
      </c:if>
      
      		<div style="margin-left: 7px;" id="menu_channel${fs.channel.id}">
      		
      			<a class="menu_channel" href="main.html?channel_id=${fs.channel.id}">${fs.channel.title}</a>
      			<a href="showCommentsToChannel.html?id=<c:out value='${fs.channel.id}'/>">#</a>
      			<a href="addComment.html?id=<c:out value='${fs.channel.id}'/>">*</a>
            	<a href="showSubscribersStatistic.html?channel_id=<c:out value='${fs.channel.id}'/>&numberWeeks=30">~</a> </td>     
      		</div>
    <c:set var="lastcategory" value="${fs.category.title}"/>  
</c:forEach>  
			</div>
		</div>
