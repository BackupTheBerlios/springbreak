<head>
<title>Remove RSS-channel</title>
<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<script type="text/javascript" src="./ajaxcore.js"></script>
<script type="text/javascript" src="./fat.js"></script>
</head>

<body>
<p>
<b>Remove a RSS-channel</b><br/><br/>

This wizard will help you to remove a RSS-channel from your subscribtion!
<br/>
</p>


<c:url var="deleteFeedURL" value="/deleteFeed.html">
	<c:param name="action" value="store" />
</c:url>

<form name="moveForm" method="post" action="${deleteFeedURL}">
  <fieldset style="width: 90%">
    <legend>Remove RSS-channel</legend>

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
    			 		<select id="category" name="category">
            				<option value="">Select Category</option> 
    							<c:forEach var="cat" items="${categories}"> 
      								<option value="${cat.id}">${cat.title}</option>
      						</c:forEach>
    					</select>
    					<input name="hcategory" id="hcategory" type="hidden" value="xxx"/>
    			 </td>
    			</tr>
    		</table>
    	</td>
    	<tr/>
    	<tr>
    	<td>
    		<table id="dfeed" style="visibility: hidden;">
    			<tr>
    				<td colspan="2">Step 2: Select RSS-Channel</td>
    			</tr>
    			<tr>
    			 <td>RSS-Channel</td>
    			 <td>  					
    				 <select id="feed" name="feed" disabled onChange="postSelFeed();">
      					<option value="">Select Feed</option>
    				</select> 
    			 </td>
    			</tr>
    		</table>
    	
    	
    	</td>
   		</tr>
    	<tr>
    	<td>
    		<table id="dsubmit" style="visibility: hidden;">
    			<tr>
    				<td>Step 3: Comfirm action</td>
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



<script type="text/javascript">
function postSelCat() {
 
  document.getElementById("hcategory").value = document.getElementById("category").value;
  document.getElementById("dfeed").style.visibility='visible';
  //document.getElementById("category").disabled = true;
  Fat.fade_element("dfeed",5, 7000, false, "#FFFFFF");
}
function postSelFeed() {
  
  document.getElementById("dsubmit").style.visibility='visible';
   document.getElementById("submit").disabled=false;
  Fat.fade_element("dsubmit",5, 7000, false, "#FFFFFF");
}

function handleHttpException() {
	alert("An exception occurred");
}

function handleEmpty() {
  document.getElementById("category").options.length = 0;
  document.getElementById("category").options[0] = new Option("None", "");
  document.getElementById("category").disabled = true;
}
</script>

<c:url var="moveURL" value="./moveFeedAjax.html" />



<ajax:select
  fieldId="category"
  targetId="feed"
  baseUrl="${moveURL}"
  paramName="make"
   postFunc="postSelCat"
  emptyFunc="handleEmpty"
  errorFunc="handleHttpException"
 />

</body>

