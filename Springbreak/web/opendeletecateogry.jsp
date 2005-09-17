<head>
<title>Remove category</title>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript" src="./fat.js"></script>

<script type="text/javascript">

function postSelCat() {
  
  document.getElementById("dsubmit").style.visibility='visible';
   document.getElementById("submit").disabled=false;
  Fat.fade_element("dsubmit",5, 7000, false, "#FFFFFF");
}
</script>

</head>

<body>
<p>
<b>Remove a category</b><br/><br/>

This wizard will help you to remove a category!<br/>

<c:url var="moveFeedURL" value="moveFeed.html">
		<c:param name="action" value="input" />
</c:url>
<c:url var="deleteFeedURL" value="deleteFeed.html">
		<c:param name="action" value="input" />
</c:url>

You can only delete empty categories! To do so you have to <a href="${moveFeedURL}">move</a> or <a href="${deleteFeedURL}">delete</a> all RSS-channels
from that category!
<br/>
</p>


<c:url var="deleteCat" value="/removeCategory.html">
	<c:param name="action" value="store" />
</c:url>

<form name="moveForm" method="post" action="${deleteCat}">
  <fieldset style="width: 90%">
    <legend>Remove category</legend>

    <table style="width: 100%">
    <tr>
    	<td>
    		<table>
    			<tr>
    				<td colspan="2">Step 1: Select category</td>
    			</tr>
    			<tr>
    			 <td>Category</td>
    			 <td>
    			 		<select id="category" name="category" onChange="postSelCat();">
            				<option value="">Select Category</option> 
    							<c:forEach var="cat" items="${categories}"> 
      								<option value="${cat.id}">${cat.title}</option>
      						</c:forEach>
    					</select>
    					
    			 </td>
    			</tr>
    		</table>
    	</td>
    	<tr/>
    	<tr>
    	<td>
    		<table id="dsubmit" style="visibility: hidden;">
    			<tr>
    				<td>Step 2: Comfirm action</td>
    			</tr>
    			<tr>
    			 <td>
    			 	<input type="submit" id="submit" name="submit" value="OK" disabled />
    			 </td>
    			</tr>
    		</table>
    	
    		
    	</td>
    </tr>
    </table>
    

    
    
    

	
    
    
  </fieldset>
</form>








</body>

