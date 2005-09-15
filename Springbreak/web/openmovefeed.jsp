<head>
<title>Move RSS-channel</title>
<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<script type="text/javascript" src="./ajaxcore.js"></script>
<script type="text/javascript" src="./fat.js"></script>
</head>

<body>
<p>
<b>Move a RSS-channel to another category!</b><br/><br/>

This wizard will help you to move a RSS-channel from one category to another one!
<br/>
</p>


<c:url var="moveFeedURL" value="/moveFeed.html">
	<c:param name="action" value="store" />
</c:url>

<form name="moveForm" method="post" action="${moveFeedURL}">
  <fieldset style="width: 90%">
    <legend>Move RSS-channel</legend>

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
    				 <select id="feed" name="feed" disabled>
      					<option value="">Select Feed</option>
    				</select> 
    			 </td>
    			</tr>
    		</table>
    	
    	
    	</td>
   		</tr>
   		<tr>
    	<td>
    	
    		<table id="dnewcat" style="visibility: hidden;">
    			<tr>
    				<td colspan="2">Step 3: Select new category</td>
    			</tr>
    			<tr>
    			 <td>New category</td>
    			 <td>  					
    				 <select id="newcat" name="newcat" disabled onChange='postSelNewCat()'>
      							<option value="">New Category</option>
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
    				<td>Step 4: Commit in database</td>
    			</tr>
    			<tr>
    			 <td>
    			 	<input type="submit" id="submit" name="submit" value"OK" disabled />
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
  document.getElementById("category").disabled = true;
  Fat.fade_element("dfeed",5, 7000, false, false);
}
function postSelFeed() {
  
   document.getElementById("dnewcat").style.visibility='visible';
  Fat.fade_element("dnewcat",5, 7000, false, false);
}

function postSelNewCat() {
  
   document.getElementById("dsubmit").style.visibility='visible';
   document.getElementById("submit").disabled=false;
  Fat.fade_element("dsubmit",5, 7000, false, false);
}

function handleHttpException() {
	alert("An exception occurred");
}

function handleEmpty() {
  document.getElementById("model").options.length = 0;
  document.getElementById("model").options[0] = new Option("None", "");
  document.getElementById("model").disabled = true;
}
</script>

<c:url var="moveURL" value="./moveFeedAjax.html">
					
</c:url>


<ajax:select
  fieldId="category"
  targetId="feed"
  baseUrl="${moveURL}"
  paramName="make"
   postFunc="postSelCat"
  emptyFunc="handleEmpty"
  errorFunc="handleHttpException"
 />
 
 <ajax:select
  fieldId="feed"
  targetId="newcat"
  baseUrl="${moveURL}"
  paramName="make2"
  postFunc="postSelFeed"
   />
</body>

