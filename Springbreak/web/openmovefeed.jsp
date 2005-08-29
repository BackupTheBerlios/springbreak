
<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<script type="text/javascript" src="./ajaxcore.js"></script>
<script type="text/javascript" src="./fat.js"></script>



<form name="moveForm" method="post" action="<c:url value="/moveFeed.html?action=store"/>">
  <fieldset>
    <legend>Move Feed Channel</legend>

    

    <label id="lcategory" for="category">Category:</label>
    <select id="category" name="category">
    	<c:forEach var="cat" items="${categories}"> 
      		<option value="${cat.id}">${cat.title}</option>
      </c:forEach>
    </select>
    <input name="hcategory" id="hcategory" type="hidden" value="xxx"/>
    

	<div id="dfeed">
    <label id="lfeed" for="feed">Feed:</label>
    <select id="feed" name="feed" disabled>
      <option value="">Select model</option>
    </select>
    </div>
    
    <div id="dnewcat">
    <label for="lnewcat">New Category:</label>
    <select id="newcat" name="newcat" disabled>
      <option value="">New Category</option>
    </select>
    </div>
    
    <input type="submit" id="submit" name="submit" value"OK" disabled />
  </fieldset>
</form>



<script type="text/javascript">
function postSelCat() {
 
  document.getElementById("hcategory").value = document.getElementById("category").value;

  document.getElementById("category").disabled = true;
  Fat.fade_element("dfeed",5, 5000, false, false);
}
function postSelFeed() {
  document.getElementById("submit").disabled = false;
  Fat.fade_element("dnewcat",5, 5000, false, false);
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



<ajax:select
  fieldId="category"
  targetId="feed"
  baseUrl="./moveFeedAjax.html"
  paramName="make"
   postFunc="postSelCat"
  emptyFunc="handleEmpty"
  errorFunc="handleHttpException"
 />
 
 <ajax:select
  fieldId="feed"
  targetId="newcat"
  baseUrl="./moveFeedAjax.html"
  paramName="make2"
  postFunc="postSelFeed"
   />


